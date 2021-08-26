package com.codecool.shop.dao.jdbc;

import com.codecool.shop.controller.DatabaseManager;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CartJdbc implements CartDao {

    DataSource dataSource = new DatabaseManager().setup();
    private LineItemDao lineItemDao;

    public CartJdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    public CartJdbc(DataSource dataSource, LineItemDao lineItemDao) throws SQLException {
        this.dataSource = dataSource;
        this.lineItemDao = lineItemDao;
    }

    @Override
    public void add(Cart cart, LineItem lineItem) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO cart (line_item_id) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, lineItem.getItemId());
            statement.executeUpdate();

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM cart WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                return null;
            }
            return new Cart(rs.getInt(1));

        }
        catch (SQLException throwable) {
            throw new RuntimeException("Error while getting Cart.", throwable);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Cart> getAll() {
        return null;
    }
}
