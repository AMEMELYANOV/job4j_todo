package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

public interface UserStore {
    User add(User user);

    User findUserByEmail(String email);
}
