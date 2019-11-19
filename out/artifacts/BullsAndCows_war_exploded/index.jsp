<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="icon" href="icon.ico">
<head>
    <meta charset="UTF-8">
    <title>Быки и коровы</title>
</head>
<body style="text-align: center">
<div>
    <h1>Быки и коровы</h1>
</div>

<div style="margin: auto">
    <div>
        <button onclick="location.href='${pageContext.request.contextPath}/list'">Рейтинг пользователей</button>
        <button onclick="location.href='${pageContext.request.contextPath}/registration'">Регистрация</button>
    </div>
    <br>
    <div style="text-align: center">
        <form method="post" action="login">
            <table style="margin: auto">
                <tr>
                    <td><label for="loginField">Логин</label></td>
                    <td><input id="loginField" type="text" name="login" required></td>
                </tr>
                <tr>
                    <td><label for="passwordField">Пароль</label></td>
                    <td><input id="passwordField" type="password" name="password" required></td>
                </tr>

                <tr>
                    <td width="50"></td>
                    <td  style="text-align: center"><input type="submit" value="Вход"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
