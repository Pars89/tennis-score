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
        <!-- Данные для первого игрока -->
        <tr>
            <td>
                <c:if test="${not empty tennisGame.firstPlayer}">
                    ${tennisGame.firstPlayer.name}
                </c:if>
            </td>
            <td>
                ${tennisGame.firstSets}
            </td>
            <td>
                ${tennisGame.firstGames}
            </td>
            <td>
                ${tennisGame.firstPoint}
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                    <input type="hidden" name="winnerId" value="${tennisGame.firstPlayer.id}">
                    <button type="submit" class="score-button">Score</button>
                </form>
            </td>
        </tr>
        <!-- Данные для второго игрока -->
        <tr>
            <td>
                <c:if test="${not empty tennisGame.secondPlayer}">
                    ${tennisGame.secondPlayer.name}
                </c:if>
            </td>
            <td>
                ${tennisGame.secondSets}
            </td>
            <td>
                ${tennisGame.secondGames}
            </td>
            <td>
                ${tennisGame.secondPoint}
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${param.uuid}">
                    <input type="hidden" name="winnerId" value="${tennisGame.secondPlayer.id}">
                    <button type="submit" class="score-button">Score</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>


</div>
</body>
</html>
