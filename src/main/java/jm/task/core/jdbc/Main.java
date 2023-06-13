package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Tomas", "Edison", (byte) 18);
        userService.saveUser("Nikola", "Tesla", (byte) 22);
        userService.saveUser("Albert", "Einstein", (byte) 25);
        userService.saveUser("Aleksandr", "Popov", (byte) 30);
        List<User> users = userService.getAllUsers();
        for (User user: users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
