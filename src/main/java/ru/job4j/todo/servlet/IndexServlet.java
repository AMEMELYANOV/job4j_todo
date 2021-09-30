package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.HbmStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IndexServlet  extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks;
        if ("true".equals(req.getParameter("completed"))) {
            tasks = HbmStore.instOf().findAll();
        } else {
            tasks = HbmStore.instOf().findNotCompleted();
        }
        resp.setContentType("application/json; charset=utf-8");
        try (OutputStream output = resp.getOutputStream()) {
            String json = GSON.toJson(tasks);
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = GSON.fromJson(req.getReader(), Task.class);
        HbmStore.instOf().add(task);
        resp.setContentType("application/json; charset=utf-8");
        try (OutputStream output = resp.getOutputStream()) {
            String json = GSON.toJson(task);
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
        }
    }
}
