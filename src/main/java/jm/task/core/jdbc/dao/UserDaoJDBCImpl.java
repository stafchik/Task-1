package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_USER_TABLE = """
                                    CREATE TABLE IF NOT EXISTS users(
                                    USER_ID SERIAL PRIMARY KEY,
                                    USERNAME VARCHAR(20) NOT NULL,
                                    LASTNAME VARCHAR(25) NOT NULL,
                                    AGE int4 NOT NULL
            )""";
    private static final String DROP_USER_TABLE = """
            DROP TABLE IF EXISTS users
                    """;
    private static final String SAVE_USER = """
            INSERT INTO users (username, lastname, age) VALUES (?,?,?)
            """;
    private static final String REMOVE_USER_BY_ID = """
            DELETE FROM users where user_id=?
            """;
    private static final String CLEAN_USERS_TABLE = """
            TRUNCATE users
            """;
    private static final String GET_ALL_USERS = """
            SELECT * FROM users
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.execute(CREATE_USER_TABLE);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.execute(DROP_USER_TABLE);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement prest = connection.prepareStatement(SAVE_USER);
            prest.setString(1, name);
            prest.setString(2, lastName);
            prest.setByte(3, age);
            System.out.println("User с именем " + name + " добавлен в таблицу");
            prest.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось добавить пользователя");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement prest = connection.prepareStatement(REMOVE_USER_BY_ID);
            prest.setLong(1, id);
            System.out.println("Пользователь удален из таблицы");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("USER_ID"));
                user.setName(resultSet.getString("username"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(userList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = Util.getStatement(connection);
            statement.execute(CLEAN_USERS_TABLE);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
