<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>ToDo Application</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="js/index.js" defer></script>
</head>
<body>
<div class="container">
    <div class="row mx-2 px-2 my-1 py-1">
        <div class="col-9">
        </div>
        <div class="col align-self-end">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/></a>
                </li>
                <c:if test="${user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <div class="row pt-2 mx-2 px-2 my-1 py-1">
        <div class="col">
                <h5>Добавить новую задачу:</h5>
                <input type="text" class="form-control" id="newDesc" name="newDesc" placeholder="Описание:">
                <div class="row mx-0 px-0 my-3 py-1">
                    <h5>Выберите категорию:</h5>
                    <select class="form-control" name="category" id="category" multiple>
                    </select>
                </div>
        </div>
    </div>
    <div class="row mx-2 px-2">
        <div class="col">
            <p><button id="submit_task" type="reset" class="btn btn-primary" onclick="addNewTask();">Создать задачу</button></p>
        </div>
    </div>
    <div class="row mx-2 px-2 mt-5 pt-1">
        <div class="col align-self-end">
            <p><h5>Список задач:</h5></p>
        </div>
        <div class="col">
        </div>
        <div class="col">
        </div>
        <div class="col align-self-end">
            <input id="completed" type="checkbox"> Показать выполненные</button>
        </div>
    </div>
    <div id="list_row" class="row mx-4 px-2 my-1 py-1">
        <table class="table table-bordered">
            <thead>
            <tr id="table_head_row">
                <th>Описание задачи</th>
                <th style="width: 25%; text-align: center" >Дата и время создания</th>
                <th style="width: 10%; text-align: center">Выполнена</th>
                <th style="width: 15%; text-align: center">Автор</th>
                <th style="width: 15%; text-align: center">Категория</th>
            </tr>
            </thead>
            <tbody id="table_body">
            </tbody>
        </table>
    </div>
</div>
</body>
