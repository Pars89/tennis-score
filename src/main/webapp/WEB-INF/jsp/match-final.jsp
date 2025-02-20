<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <h2>Winner: ${winner}</h2>
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
        <tr>
            <td>${playerName1}</td>
            <td>${sets1}</td>
            <td>${games1}</td>
            <td>${points1}</td>
        </tr>
        <tr>
            <td>${playerName2}</td>
            <td>${sets2}</td>
            <td>${games2}</td>
            <td>${points2}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
