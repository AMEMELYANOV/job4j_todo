package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface Store {
    Task add(Task task);

    List<Task> findAll();

    boolean saveTaskStatus(int id, boolean taskState);

    List<Task> findNotCompleted();
}
