package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Petr", "Petr", (byte) 20);
        userService.saveUser("Ivan", "Ivan", (byte) 40);
        userService.saveUser("Mihail", "Mihail", (byte) 60);
        userService.saveUser("Sergey", "Sergey", (byte) 80);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
