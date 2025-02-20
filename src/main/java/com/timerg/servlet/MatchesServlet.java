package com.timerg.servlet;

import com.timerg.dto.MatchReadDto;
import com.timerg.entity.TennisGame;
import com.timerg.service.MatchService;
import com.timerg.service.PlayerService;
import com.timerg.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private static final Integer PAGE_SIZE = 3;
    private final MatchService matchService = MatchService.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageParam = req.getParameter("page");
        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String filterByName = req.getParameter("filter_by_player_name");
        if (filterByName == null) {
            filterByName = "";

        }
        req.setAttribute("player_name", filterByName);

        List<MatchReadDto> matches = matchService.findMatchesBySimilarName(filterByName, page, PAGE_SIZE);
        int totalMatches = matchService.countMatchesBySimilarName(filterByName);
        // Вычисляем общее количество страниц
        int totalPages = (int) Math.ceil((double) totalMatches / PAGE_SIZE);

        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        String playerName = req.getParameter("player_name");
//        resp.sendRedirect(req.getContextPath() +"/matches?filter_by_player_name=" + playerName);
//    }
}
