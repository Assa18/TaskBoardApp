package edu.bbte.idde.mtim2378.service;

import edu.bbte.idde.mtim2378.service.impl.TodoServiceImpl;

public class TodoServiceFactory {
    private static TodoService instance;

    public static synchronized TodoService getInstance() {
        if (instance == null) {
            instance = new TodoServiceImpl();
        }

        return instance;
    }
}
