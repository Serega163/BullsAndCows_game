<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<link rel="icon" href="icon.ico">
<head>
    <title>Быки и коровы</title>
</head>
<body style="text-align: center">
<% String userLogin = (String) request.getAttribute("login");%>
<pre><a href="${pageContext.request.contextPath}">На главную</a>
<img src="image/22231cowface_98730.png" width="180" height="180" style="float: left; margin-left: 10%"><h2
            style="margin: 0px"></h2><img src="image/455cowface_100996.png" width="180" height="180"
                                          style="float: right ; margin-right: 10%">
Если в названном тобой числе есть цифра, которая присутствует
в загаданном, и она стоит на своем месте, то это 1 бык.
А если такая цифра есть, но не на своем месте - то это одна корова.
Анализируя, сколько быков и коров в названном тобой числе, подбери ответ.
Цифры в числе не повторяются. Первая цифра не ноль.
Пример: если загадано число 1234, а ты назвал 1463,
то это 1Б (1 стоит на своем месте) и 2К (4 и 3 есть, но стоят не на своих местах)
</pre>

<table style="margin: auto">
    <tr>
        <form method="post" action="game" style="${isWin ? 'visibility:collapse' : 'visibility:visible'}">
            <td><input style="${isWin ? 'visibility:hidden' : 'visibility:visible'}" type="number" min="1023" max="9876" step="1" id="loginField" type="text" name="numberOffer"
                       required>
                <input type="hidden" name="login" value="<%=userLogin%>"/>
                <!-- для передачи логина пользователя на сервлет -->
            </td>
            <td style="text-align: center; ${isWin ? 'visibility:hidden' : 'visibility:visible'}"><input type="submit" value="Проверить"></td>
        </form>
    </tr>
    <tr>
        <td colspan="2">
            <form method="post" action="game" style="margin-bottom: 0px">
                <p style="text-align: center;  visibility: collapse">
                    <button type="submit" name="newGame"
                            style="${isWin ? 'visibility:visible' : 'visibility:collapse'}">Новая игра
                    </button><!--отображение кнопки "новая игра" в случае победы-->
                </p>
            </form>
        </td>
    </tr>
</table>
<table style="margin: auto">
    <tr>
        <td style="text-align: center"><pre><%

            String allNumbers = "";
            String number = (String) request.getAttribute("allTry");
            if (number != null) {//Чтбы первый раз не выводить null
                allNumbers = number;
            }
            Boolean isWin;
            isWin = (Boolean) request.getAttribute("isWin");
            out.print(allNumbers);
        %></pre>
        </td>

    </tr>
</table>
</body>
</html>
