package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "Create table users" +
                    "(" +
                    "id int AUTO_INCREMENT PRIMARY KEY," +
                    "name varchar(45) not null," +
                    "lastname varchar(45) not null," +
                    "age int(10) not null" +
                    ");";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO users VALUES(" + user.getId() + ",'" + user.getName() + "','" +
                    user.getLastName() + "'," + user.getAge() + ")";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM USERS WHERE id =" + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM Users";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setAge(resultSet.getByte("age"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM USERS";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
