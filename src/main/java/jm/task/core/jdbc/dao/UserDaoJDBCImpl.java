package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS users";
    private static final String DROP = "DROP TABLE IF EXISTS users";
    private static final String SAVE = "INSERT INTO users(name, lastName, age) VALUES(?,?,?)";
    private static final String REMOVE = "DELETE FROM users WHERE id = ?";
    private static final String GET = "SELECT * FROM users";
    private static final String CLEAN = "TRUNCATE TABLE users";

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SAVE);
            ps.setString(1, name);
            ps.setString(1, lastName);
            ps.setByte(1, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()){
            PreparedStatement ps = connection.prepareStatement(REMOVE);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()){
            PreparedStatement ps = connection.prepareStatement(GET);
            ResultSet rs = ps.executeQuery();
            while (rs.next() ) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
