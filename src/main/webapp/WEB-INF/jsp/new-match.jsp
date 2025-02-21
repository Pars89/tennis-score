<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание нового матча</title>
    <style>
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 40px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 8px;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        .error {
            color: red;
            margin-top: 5px;
            font-size: 14px;
        }
        button {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #45a049;
        }
        .error-message {
            text-align: center;
            color: red;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Создание нового матча</h2>
    <form action="${pageContext.request.contextPath}/new-match" method="post">
        <div class="form-group">
            <label for="player1">Имя игрока 1:</label>
            <input type="text" name="player1" id="player1" value="${player1 != null ? player1 : ''}">
            <c:if test="${not empty player1Error}">
                <div class="error">${player1Error}</div>
            </c:if>
        </div>
        <div class="form-group">
            <label for="player2">Имя игрока 2:</label>
            <input type="text" name="player2" id="player2" value="${player2 != null ? player2 : ''}">
            <c:if test="${not empty player2Error}">
                <div class="error">${player2Error}</div>
            </c:if>
        </div>
        <button type="submit">Начать матч</button>
    </form>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
</div>
</body>
</html>

