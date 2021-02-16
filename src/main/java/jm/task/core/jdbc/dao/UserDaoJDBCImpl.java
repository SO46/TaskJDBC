package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    connection.prepareStatement(
                            "CREATE TABLE `db_1`.`users` (" +
                                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                                    "  `name` VARCHAR(45) NOT NULL," +
                                    "  `lastName` VARCHAR(45) NULL," +
                                    "  `age` TINYINT(3) NOT NULL," +
                                    "  PRIMARY KEY (`id`)" +
                                    ") ENGINE=InnoDB DEFAULT CHARACTER SET = utf8");

            preparedStatement.execute();
        } catch (SQLException e){
            System.out.println("Table is not created or already exists");
        } finally {
            close(preparedStatement);
        }
    }

    public void dropUsersTable() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users");
            preparedStatement.execute();
        } catch (SQLException e){
            System.err.println("Table is not dropped or doesnt exist");
        } finally {
            close(preparedStatement);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age)  VALUES(?, ?, ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(preparedStatement);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return userList;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("TRUNCATE TABLE users");
            preparedStatement.execute();
        } catch (SQLException e){
            System.err.println("Data deletion error");
        } finally {
            close(preparedStatement);
        }
    }

    private void close(Statement preparedStatement){
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
