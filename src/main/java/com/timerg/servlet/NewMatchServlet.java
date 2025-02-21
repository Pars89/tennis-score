package com.timerg.servlet;

import com.timerg.exception.PlayerNameLengthException;
import com.timerg.dto.PlayerReadDto;
import com.timerg.exception.PlayerPlayingAgainstSelfException;
import com.timerg.service.MatchService;
import com.timerg.service.PlayerService;
import com.timerg.util.JspHelper;
import com.timerg.validation.PlayerNameValidator;
import com.timerg.validation.ValidationResult;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.timerg.dto.PlayerCreateDto;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebServlet(value = "/new-match")
public class NewMatchServlet extends HttpServlet {
    private final PlayerService playerService = PlayerService.getInstance();
    private final PlayerNameValidator playerNameValidator = PlayerNameValidator.getInstance();
    private final MatchService matchService = MatchService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JspHelper.getPath("new-match"))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String playerName1 = request.getParameter("player1");
        String playerName2 = request.getParameter("player2");

        ValidationResult validationName1 = playerNameValidator.isValid(playerName1);
        ValidationResult validationName2 = playerNameValidator.isValid(playerName2);

        try {
            if (!validationName1.isValid() || !validationName2.isValid()) {
                throw new PlayerNameLengthException();
            }

            if (playerName1.equals(playerName2)) {
                throw new PlayerPlayingAgainstSelfException("Ошибка: игрок не может играть с самим собой");
            }

            var playerDto1 = PlayerCreateDto.builder().name(playerName1).build();
            var playerDto2 = PlayerCreateDto.builder().name(playerName2).build();

            PlayerReadDto playerReadDto1 = playerService.getOrCreatePlayer(playerDto1);
            PlayerReadDto playerReadDto2 = playerService.getOrCreatePlayer(playerDto2);

            // Так как я планирую добавить новый сервис OnGoingMatch, то здесь не будет matchService
            UUID uuid = matchService.createNewMatch(playerReadDto1, playerReadDto2);

            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);

        } catch (PlayerNameLengthException e) {
            log.error("NewMatchServlet, doPost() error: " + e.getMessage());
            // Устанавливаем сообщение об ошибке в атрибут запроса
            request.setAttribute("errorMessage", e.getMessage());
            if (!validationName1.isValid()) {
                request.setAttribute("player1Error", validationName1.getErrorResponses().get(0).getMessage());
            }
            if (!validationName2.isValid()) {
                request.setAttribute("player2Error", validationName2.getErrorResponses().get(0).getMessage());
            }
            // Чтобы сохранить введённые значения, можно установить их тоже как атрибуты:
            request.setAttribute("player1", playerName1);
            request.setAttribute("player2", playerName2);
            // Перенаправляем обратно на страницу формы
            request.getRequestDispatcher(JspHelper.getPath("new-match"))
                    .forward(request, response);
        } catch (PlayerPlayingAgainstSelfException e) {
            log.error("NewMatchServlet, doPost() error: " + e.getMessage());
            // Устанавливаем сообщение об ошибке в атрибут запроса
            request.setAttribute("errorMessage", e.getMessage());
            // Чтобы сохранить введённые значения, можно установить их тоже как атрибуты:
            request.setAttribute("player1", playerName1);
            request.setAttribute("player2", playerName2);
            // Перенаправляем обратно на страницу формы
            request.getRequestDispatcher(JspHelper.getPath("new-match"))
                    .forward(request, response);
        } catch (Exception e) {
            log.error("NewMatchServlet, doPost() error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }

    }
}
