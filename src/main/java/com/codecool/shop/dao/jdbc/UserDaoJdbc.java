package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDaoJdbc implements UserDao {

    private DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getName());
            st.setString(2, user.getPassword());
            st.executeUpdate();
        } catch (SQLException throwable) {
            throw new RuntimeException("Error", throwable);
        }
    }

    @Override
    public User find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            User user = new User (resultSet.getString("name"), resultSet.getString("password"));
            user.setId(resultSet.getInt("id"));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error wile getting user with id: " +id +e);
        }
    }

    @Override
    public void remove(int id) {

    }
}
