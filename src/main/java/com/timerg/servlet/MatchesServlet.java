package com.timerg.servlet;

import com.timerg.dto.MatchReadDto;
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

@Slf4j
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private final MatchService matchService = MatchService.getInstance();
    private final PlayerService playerService = PlayerService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pageParam = req.getParameter("page");
        String filterByName = req.getParameter("filter_by_player_name");

        // validation
        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (filterByName == null) {
            filterByName = "";

        }

        List<MatchReadDto> matches = matchService.findMatchesBySimilarName(filterByName, page);
        int totalMatches = matchService.countMatchesBySimilarName(filterByName);
        int totalPages = matchService.countPagesBySimilarName(totalMatches);

        req.setAttribute("player_name", filterByName);
        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher(JspHelper.getPath("matches"))
                .forward(req, resp);
    }
}
