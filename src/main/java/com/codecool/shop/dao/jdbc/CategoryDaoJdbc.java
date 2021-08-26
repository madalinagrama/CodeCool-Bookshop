package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.CategoryDao;
import com.codecool.shop.dao.implementation.CategoryDaoMem;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {
    DataSource dataSource = new DatabaseManager().setup();
    private static CategoryDaoJdbc instance = null;

    public CategoryDaoJdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    CategoryDaoJdbc() throws SQLException{
    }

    public static CategoryDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new CategoryDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO  product_category(name, description, department) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setString(3, category.getDepartment());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            category.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT * FROM product_category WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            var category = new ProductCategory(rs.getString("name"), rs.getString("description"), rs.getString("department"));
            category.setId(id);

            return category;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "DELETE FROM product_category WHERE id = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM product_category";

            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ProductCategory> productCategories = new ArrayList<>();

            while(rs.next()) {
                ProductCategory temp = new ProductCategory(rs.getString("name"), rs.getString("department"), rs.getString("description"));
                int id =  rs.getInt("id");
                temp.setId(id);
                productCategories.add(temp);
            }
            return productCategories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
