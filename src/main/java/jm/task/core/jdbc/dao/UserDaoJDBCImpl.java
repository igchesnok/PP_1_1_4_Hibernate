package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection  connection = Util.getConnection();
                    Statement statement = connection.createStatement()) {

            statement.executeUpdate( "CREATE TABLE IF NOT EXISTS `stydent`.`" + Util.TABLE_USER + "` (\n" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT(3) NULL,\n" +
                    "  PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection  connection = Util.getConnection();
                    Statement statement = connection.createStatement()) {

            statement.executeUpdate("drop table IF EXISTS " + Util.TABLE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanUsersTable() {
        try (Connection  connection = Util.getConnection();
                    Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE " + Util.TABLE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection  connection = Util.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                            Util.TABLE_USER + " (name, lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection  connection = Util.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " +
                            Util.TABLE_USER + " WHERE id=?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection  connection = Util.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + Util.TABLE_USER);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
