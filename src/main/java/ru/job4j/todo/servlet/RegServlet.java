package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmUserStore;
import ru.job4j.todo.store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        UserStore store = HbmUserStore.instOf();
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if ("".equals(name.trim()) || "".equals(email.trim()) || "".equals(password.trim())) {
            req.setAttribute("error", "Некорректное заполнение полей");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (store.findUserByEmail(email) == null) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            store.add(user);
            resp.sendRedirect("login.jsp");
        } else {
            req.setAttribute("error", "Пользователь с таким email уже зарегистрирован");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}