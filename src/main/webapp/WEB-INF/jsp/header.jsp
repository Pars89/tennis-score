<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Tennis App</title>
  <style>
    /* Стили для навигационного меню */
    .navbar {
      background-color: #333;
      overflow: hidden;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    .navbar a {
      float: left;
      display: block;
      color: #f2f2f2;
      text-align: center;
      padding: 14px 20px;
      text-decoration: none;
      font-size: 16px;
      transition: background 0.3s;
    }
    .navbar a:hover {
      background-color: #555;
    }
    .navbar a.active {
      background-color: #4CAF50;
    }
  </style>
</head>
<body>
<div class="navbar">
  <a class="active" href="<%= request.getContextPath() %>/">Home</a>
  <a href="<%= request.getContextPath() %>/matches">Completed Matches</a>
</div>
<!-- Header остается открытым, основной контент идет ниже -->
