package ru.job4j.todo.servlet;

import ru.job4j.todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskDone extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String state = request.getParameter("done");
        boolean done = "true".equals(state);
        HbmStore.instOf().saveTaskStatus(id, done);
    }
}
