package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnectionToDatabase;

public class UserDaoJDBCImpl implements UserDao {

//    private static final String URL = "jdbc:mysql://localhost:3306/users";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "password";
    private static final Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    static {
            connection = getConnectionToDatabase();
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement = connection.createStatement();
            String sql = "CREATE TABLE users" +
                    "(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL," +
                    "age TINYINT(10) NOT NULL" +
                    ")";
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
            statement.executeUpdate("START TRANSACTION");
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users VALUES(0, ?, ? ,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
            statement.executeUpdate("COMMIT");


            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("START TRANSACTION");
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM users WHERE id = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            statement.executeUpdate("COMMIT");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM Users;";
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
            statement.executeUpdate("START TRANSACTION");
            String sql = "DELETE FROM USERS";
            statement.executeUpdate(sql);
            statement.executeUpdate("COMMIT");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
