package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private DataSource dataSource;
    Gson gson = new Gson();

    public ProductDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO  product(name, description, default_price, currency_string, supplier, product_category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getDefaultPrice());
            statement.setString(4, product.getDefaultCurrency().toString());
            statement.setString(5, product.getSupplier().toString());
            statement.setString(6, product.getProductCategory().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
//            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Product(rs.getString(1), rs.getFloat(2), rs.getString(3), rs.getString(4), rs.getObject(5, ProductCategory.class), rs.getObject(6, Supplier.class));
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            CategoryDaoJdbc categoryDaoJdbc = new CategoryDaoJdbc();
            SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
            String sql = "SELECT * FROM product";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()) {
                ProductCategory category = categoryDaoJdbc.find(rs.getInt(5));
                Supplier supplier = supplierDaoJdbc.find(6);
                Product product = new Product(rs.getString(1), rs.getFloat(2), rs.getString(3), rs.getString(4), category, supplier);
                result.add(product);
            }
            System.out.println(result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
