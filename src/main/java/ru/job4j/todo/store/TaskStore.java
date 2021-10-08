package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;

public interface TaskStore {
    Task add(Task task, List<Integer> cIdc);

    List<Task> findAll();

    boolean saveTaskStatus(int id, boolean taskState);

    List<Task> findNotCompleted();

    List<Category> findAllCategories();
}
