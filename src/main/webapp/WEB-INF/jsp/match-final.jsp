<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title> Final score </title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Match completed</h1>
    <h2>Winner: ${tennisGame.winnerPlayer.name}</h2>

    <table>
        <thead>
        <tr>
            <th>Player</th>
            <th>Sets</th>
            <th>Games</th>
            <th>Points</th>
        </tr>
        </thead>
        <tbody>
        <!-- Данные первого игрока -->
        <tr>
            <td>${tennisGame.firstPlayer.name}</td>
            <td>${tennisGame.firstSets}</td>
            <td>${tennisGame.firstGames}</td>
            <td>${tennisGame.firstPoint}</td>
        </tr>
        <!-- Данные второго игрока -->
        <tr>
            <td>${tennisGame.secondPlayer.name}</td>
            <td>${tennisGame.secondSets}</td>
            <td>${tennisGame.secondGames}</td>
            <td>${tennisGame.secondPoint}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
