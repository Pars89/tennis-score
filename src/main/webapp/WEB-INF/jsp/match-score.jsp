<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Match</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            background: #fff;
            margin: 0 auto;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f0f0f0;
        }
        .score-button {
            background-color: #28a745;
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .score-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Match</h1>
    <table>
        <thead>
        <tr>
            <th>Player</th>
            <th>Sets</th>
            <th>Games</th>
            <th>Points</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <c:if test="${not empty playerName1}">
                    ${playerName1}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty sets1}">
                    ${sets1}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty games1}">
                    ${games1}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty points1}">
                    ${points1}
                </c:if>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                    <!-- Передаем идентификатор первого игрока (например, его id или просто строку "first") -->
                    <input type="hidden" name="winnerId" value="${player1Id}">
                    <button type="submit" class="score-button">Score</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${not empty playerName2}">
                    ${playerName2}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty sets2}">
                    ${sets2}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty games2}">
                    ${games2}
                </c:if>
            </td>
            <td>
                <c:if test="${not empty points2}">
                    ${points2}
                </c:if>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                    <input type="hidden" name="winnerId" value="${player2Id}">
                    <button type="submit" class="score-button">Score</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
