<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создание нового матча</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/new-match" method="post" >

        <label for="player1"> Имя игрока 1:
            <input type="text" name="player1" id="player1" value="${player1 != null ? player1 : ''}">
        </label> <br>

        <c:if test="${not empty player1Error}">
            <span style="color: red;">${player1Error}</span>
        </c:if>

        <br> <br>

        <label for="player2"> Имя игрока 2:
            <input type="text" name="player2" id="player2" value="${player2 != null ? player2 : ''}">
        </label>  <br>

        <c:if test="${not empty player2Error}">
            <span style="color: red;">${player2Error}</span>
        </c:if>

        <br> <br>

        <button type="submit"> Начать </button>

    </form>

    <!-- Если есть сообщение об ошибке, выводим его красным -->
    <c:if test="${not empty errorMessage}">
        <div style="color: red;">
                ${errorMessage}
        </div>
    </c:if>
</body>
</html>
