package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("ivan", "Ivanob", (byte) 23);
        userService.saveUser("Petr", "Petrov", (byte) 42);
        userService.saveUser("Alexandr", "Alexandrov", (byte) 19);
        userService.saveUser("Dmitry", "Ivanov", (byte) 33);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
