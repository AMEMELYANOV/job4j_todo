package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmTaskStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IndexServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks;
        List<Category> categories = HbmTaskStore.instOf().findAllCategories();
        if ("true".equals(req.getParameter("completed"))) {
            tasks = HbmTaskStore.instOf().findAll();
        } else {
            tasks = HbmTaskStore.instOf().findNotCompleted();
        }
        resp.setContentType("application/json; charset=utf-8");
        try (OutputStream output = resp.getOutputStream()) {
            String json1 = GSON.toJson(tasks);
            String json2 = GSON.toJson(categories);
            String json = "[" + json1 + "," + json2 + "]";
            output.write(json.getBytes(StandardCharsets.UTF_8));
            output.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Task task = new Task();
        String describe = req.getParameter("describe");
        if (req.getParameter("describe") != null) {
            task.setDescribe(describe);
        }
        String jsonOutput = req.getParameter("cIdc");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Integer>>() { }.getType();
        List<Integer> cIdc = gson.fromJson(jsonOutput, listType);

        HttpSession sc = req.getSession();
        if (sc.getAttribute("user") != null) {
            User user = (User) sc.getAttribute("user");
            task.setUser(user);
        }
        HbmTaskStore.instOf().add(task, cIdc);
        doGet(req, resp);
    }
}
