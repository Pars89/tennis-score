package com.timerg.servlet;

import com.timerg.dto.MatchCreateDto;
import com.timerg.entity.StatusGame;
import com.timerg.entity.TennisGame;
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
    private final ConcurrentMap<UUID, TennisGame> currentMatches = MatchService.getCurrentMatches();
    private final MatchService matchService = MatchService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        if (uuidParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указан параметр uuid");
            return;
        }

        UUID matchUUID = UUID.fromString(uuidParam);
        TennisGame tennisGame = currentMatches.get(matchUUID);

        if (tennisGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }

        if (isWinner(tennisGame.getStatusGame())) {
            // Удаляем матч из коллекции текущих матчей
            currentMatches.remove(matchUUID);
            // Записываем законченный матч в SQL базу данных
            MatchCreateDto matchCreateDto = MatchCreateDto.builder()
                    .firstPlayerReadDto(tennisGame.getFirstPlayer())
                    .secondPlayerReadDto(tennisGame.getSecondPlayer())
                    .winnerPlayerReadDto(tennisGame.getWinnerPlayer())
                    .build();

            matchService.save(matchCreateDto);

            // Рендерим финальный счёт
            req.setAttribute("playerName1", tennisGame.getFirstPlayer().getName());
            req.setAttribute("playerName2", tennisGame.getSecondPlayer().getName());
            req.setAttribute("sets1", tennisGame.getFirstSets());
            req.setAttribute("sets2", tennisGame.getSecondSets());
            req.setAttribute("games1", tennisGame.getFirstGames());
            req.setAttribute("games2", tennisGame.getSecondGames());
            req.setAttribute("points1", tennisGame.getFirstPoint());
            req.setAttribute("points2", tennisGame.getSecondPoint());
            req.setAttribute("winner",
                    tennisGame.getStatusGame() == StatusGame.FIRST_WINNER
                            ? tennisGame.getFirstPlayer().getName()
                            : tennisGame.getSecondPlayer().getName()
            );

            // Перенаправляем на страницу финального счета
            req.getRequestDispatcher(JspHelper.getPath("match-final"))
                    .forward(req, resp);
            return;

        } else {
            req.setAttribute("playerName1", tennisGame.getFirstPlayer().getName());
            req.setAttribute("sets1", tennisGame.getFirstSets());
            req.setAttribute("games1", tennisGame.getFirstGames());
            req.setAttribute("points1", tennisGame.getFirstPoint());
            req.setAttribute("player1Id", tennisGame.getFirstPlayer().getId());

            req.setAttribute("playerName2", tennisGame.getSecondPlayer().getName());
            req.setAttribute("sets2", tennisGame.getSecondSets());
            req.setAttribute("games2", tennisGame.getSecondGames());
            req.setAttribute("points2", tennisGame.getSecondPoint());
            req.setAttribute("player2Id", tennisGame.getSecondPlayer().getId());
        }

        req.getRequestDispatcher(JspHelper.getPath("match-score"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uuid = req.getParameter("uuid");
        TennisGame tennisGame = currentMatches.get(UUID.fromString(uuid));

        if (tennisGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }

        String winnerId = req.getParameter("winnerId");

        if (winnerId.equals(String.valueOf(tennisGame.getFirstPlayer().getId()))) {
            tennisGame.addPointFirstPlayer();
        } else if (winnerId.equals(String.valueOf(tennisGame.getSecondPlayer().getId()))){
            tennisGame.addPointSecondPlayer();
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный идентификатор игрока");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }

    private boolean isWinner(StatusGame statusGame) {
        return !StatusGame.ONGOING.equals(statusGame);
    }
}
