<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="icon" href="icon.ico">
<head>
    <title>Регистрация</title>
</head>
    <script LANGUAGE="JavaScript">


        function checkPw(form) {
            var password1 = form.password1.value;
            var password2 = form.password2.value;

            if (password1 != password2) {
                alert("\nПароли не совпадают!")
                return false;
            } else return true;
        }

    </script>

<body style="text-align: center">
<div>
    <h3>Регистрация</h3>
    Вернуться на <a href="${pageContext.request.contextPath}">главную</a>
    <br>
    <div style="width:700px; margin: 0 auto">
        <form method="post" action="registration" onsubmit="return checkPw(this)" style=" padding: 0 0 0 150px">
        <table >
            <tr>
                <td align="right"><label for="loginField">Логин</label></td>
                <td><input align="right" id="loginField" type="text" name="login" pattern="[0-9a-zA-Z]{3,15}" required></td>
                <td>Латинские буквы и цифры. Минимум 3 символа</td>
            </tr>
            <tr>
                <td align="right"><label for="passwordField1">Пароль</label></td>
                <td><input id="passwordField1" type="password" name="password1" minlength="4" required></td>
                <td>Минимум 4 символа</td>
            </tr>
            <tr>
                <td align="right"><label for="passwordField2">Повторите пароль</label></td>
                <td><input id="passwordField2" type="password" name="password2" minlength="4" required></td>
            </tr>
            <tr>
                <td></td>
                <td style="text-align: center "><input type="submit" value="Зарегистрироваться"></td>
            </tr>
        </table>
    </form>
    </div>
</div>

</body>
</html>
