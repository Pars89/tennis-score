<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Tennis App</title>
    <style>
        .container {
            max-width: 800px;
            margin: 60px auto;
            text-align: center;
            padding: 20px;
        }
        .container h1 {
            margin-bottom: 40px;
            font-size: 36px;
            color: #333;
        }
        .btn {
            display: inline-block;
            margin: 20px;
            padding: 15px 30px;
            font-size: 18px;
            text-decoration: none;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to Tennis App</h1>
    <a class="btn" href="<%= request.getContextPath() %>/new-match">Create New Match</a>
    <a class="btn" href="<%= request.getContextPath() %>/matches">View Completed Matches</a>
</div>
</body>
</html>
