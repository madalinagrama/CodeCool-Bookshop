package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class LineItemJdbc implements LineItemDao {

    private DataSource dataSource;

    public LineItemJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(LineItem lineItem) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO line_item (order_id, quantity, item_id, currency, unit_price, product, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, lineItem.getOrderId());
            statement.setInt(2,lineItem.getQuantity());
            statement.setInt(3, lineItem.getItemId());
            statement.setString(4, lineItem.getCurrency());
            statement.setFloat(5, lineItem.getUnitPrice());
            statement.setObject(6, lineItem.getProduct());
            statement.setFloat(7, lineItem.getTotal());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//
    @Override
    public LineItem find(int id) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM line_item WHERE item_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                return null;
            }
            return new LineItem(rs.getInt(1));

        }
        catch (SQLException e) {
            throw new RuntimeException("Error while finding the line item.", e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getBy(int id) {
        return null;
    }

    @Override
    public List<LineItem> getAll() {
        return null;
    }
}
