<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Калькулятор</title>
</head>
<body>
    <h1>Калькулятор доходности</h1>
    <form action="/finance" method="post">
<style>.color-text { background-color: #cfd1cd;  } </style>
         Сумма на момент открытия <input type='text' name='sum' class='color-text' />
        <br/>
         Процентная ставка <input type='text' input name='percentage' class='color-text'/>
        <br/>
        Количество лет <input type='text' input name='years' class='color-text' />
        <br/>
        <input type="submit" value="Посчитать" style='background-color: #1e90ff' />
    </form>
</body>
</html>