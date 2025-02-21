<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Completed matches</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
        }
        form {
            margin-bottom: 20px;
            text-align: center;
        }
        input[type="text"] {
            padding: 8px;
            width: 250px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            padding: 8px 16px;
            border: none;
            background-color: #28a745;
            color: #fff;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #f0f0f0;
        }
        .pagination { text-align: center; margin-top: 20px; }
        .pagination a { padding: 8px 12px; margin: 0 4px; border: 1px solid #ccc; text-decoration: none; color: #333; border-radius: 4px; }
        .pagination a.active { background-color: #28a745; color: #fff; }
        .pagination a:hover { background-color: #218838; color: #fff; }
    </style>
</head>
<body>
<div class="container">
    <h1>Completed matches</h1>
    <!-- Форма фильтра по имени игрока -->
    <form method="get" action="${pageContext.request.contextPath}/matches">
        <input type="text" name="filter_by_player_name" placeholder="Введите имя игрока"
               value="${param.filter_by_player_name}" />
        <button type="submit">Искать</button>
    </form>
    <!-- Таблица матчей -->
    <c:if test="${not empty matches}">
        <table>
            <thead>
            <tr>
                <th>Player 1</th>
                <th>Player 2</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="match" items="${matches}">
                <tr>
                    <td>${match.firstPlayerReadDto.name}</td>
                    <td>${match.secondPlayerReadDto.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty match.winnerPlayerReadDto}">
                                ${match.winnerPlayerReadDto.name}
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty matches}">
        <p>Matches not found.</p>
    </c:if>

    <!-- Пагинация -->
    <c:if test="${totalPages > 1}">
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage - 1}&filter_by_player_name=${param.filter_by_player_name}">Previous</a>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="${pageContext.request.contextPath}/matches?page=${i}&filter_by_player_name=${param.filter_by_player_name}"
                   class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage + 1}&filter_by_player_name=${param.filter_by_player_name}">Next</a>
            </c:if>
        </div>
    </c:if>
</div>
</body>
</html>