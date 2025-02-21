package com.timerg.servlet;

import com.timerg.dto.MatchCreateDto;
import com.timerg.entity.StatusGame;
import com.timerg.entity.TennisGame;
import com.timerg.exception.MatchNotFoundException;
import com.timerg.exception.PlayerWinnerNotFoundException;
import com.timerg.service.MatchService;
import com.timerg.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final MatchService matchService = MatchService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");

        try {
            // validation uuidParam

            // Поменять на OnGoingMatchService
            TennisGame tennisGame = matchService.getTennisGame(uuidParam);

            req.setAttribute("tennisGame", tennisGame);

            req.getRequestDispatcher(JspHelper.getPath("match-score"))
                    .forward(req, resp);
        } catch (MatchNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: match not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uuidParam = req.getParameter("uuid");
        String winnerId = req.getParameter("winnerId");

        try {
            // validation uuidParam и winnerId

            matchService.addPointWinner(uuidParam, winnerId);

            if(matchService.checkWinner(uuidParam)) {
                // For final rendering
                TennisGame tennisGame = matchService.getTennisGame(uuidParam);
                // Мне кажется нужно добавить новый сервис для текущих матчей
                // Чтобы не смешивать matchService и OnGoingMatchService
                // Удаляем матч из коллекции текущих матчей
                matchService.getCurrentMatches().remove(uuidParam); // потом удалить
                // Записываем законченный матч в SQL базу данных
                MatchCreateDto matchCreateDto = MatchCreateDto.builder()
                        .firstPlayerReadDto(tennisGame.getFirstPlayer())
                        .secondPlayerReadDto(tennisGame.getSecondPlayer())
                        .winnerPlayerReadDto(tennisGame.getWinnerPlayer())
                        .build();

                matchService.save(matchCreateDto);

                // Рендерим финальный счёт
                req.setAttribute("tennisGame", tennisGame);

                // Перенаправляем на страницу финального счета
                req.getRequestDispatcher(JspHelper.getPath("match-final"))
                        .forward(req, resp);
                return;
            } else {
                resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuidParam);
            }

        } catch (MatchNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: match not found");
        } catch (PlayerWinnerNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: player winner not found");
        }
    }
}
