<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="icon" href="icon.ico">
<head>
    <title>Список игроков</title>
</head>
<body style="text-align: center">
<h3>Рейтинг игроков</h3>
Вернуться на <a href="${pageContext.request.contextPath}">главную</a>
<br>
<table border="1" style="margin: auto; text-align: center">
    <tr>
        <td>В среднем попыток</td>
        <td>Пользователь</td>
        <td>Сыграно раз</td>
        <br>
    </tr>
    <%
        String usersCount = request.getAttribute("usersCount").toString();
        System.out.println(usersCount);
        String countGames;
        String login;
        String avgScore;
        for (int i = 0; i < Integer.parseInt(usersCount); i++) {
            countGames = request.getAttribute("countGames" + i).toString();
            login = request.getAttribute("login" + i).toString();
            avgScore = request.getAttribute("avgScore" + i).toString();
    %>
    <tr>
        <td><%= countGames %>
        </td>
        <td><%= login %>
        </td>
        <td><%= avgScore %>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
