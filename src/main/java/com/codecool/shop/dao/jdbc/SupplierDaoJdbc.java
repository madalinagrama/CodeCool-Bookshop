package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {
    DataSource dataSource = new DatabaseManager().setup();
    private static SupplierDaoJdbc instance = null;

    public SupplierDaoJdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    SupplierDaoJdbc() throws SQLException {
    }

    public static SupplierDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM supplier WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Supplier(rs.getString(1), rs.getString(2));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM supplier";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Supplier> result = new ArrayList<>();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                Supplier temp = new Supplier(rs.getString("name"), rs.getString("description"));
                int id = rs.getInt("id");
                temp.setId(id);
                result.add(temp);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
