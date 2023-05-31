package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Tomas", "Edison", (byte) 18);
        userService.saveUser("Nikola", "Tesla", (byte) 22);
        userService.saveUser("Albert", "Einstein", (byte) 25);
        userService.saveUser("Aleksandr", "Popov", (byte) 30);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
