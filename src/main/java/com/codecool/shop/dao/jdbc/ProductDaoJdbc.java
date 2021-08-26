package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    DataSource dataSource = new DatabaseManager().setup();
    private static ProductDaoJdbc instance = null;

    public ProductDaoJdbc(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    ProductDaoJdbc() throws SQLException {
    }

public static ProductDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
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
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                ProductCategory category = categoryDaoJdbc.find(7);
                Supplier supplier = supplierDaoJdbc.find(6);
                Product temp = new Product(rs.getString("name"), rs.getFloat("default_price"), rs.getString("currency_string"), rs.getString("description"), category, supplier);
                int id = rs.getInt("id");
                temp.setId(id);
                products.add(temp);
            }
            System.out.println(products);
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try (Connection conn = dataSource.getConnection()){
            CategoryDaoJdbc categoryDaoJdbc = new CategoryDaoJdbc();
            String sql = "SELECT * FROM product WHERE supplier = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, supplier.getId());
            ResultSet rs = ps.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                ProductCategory productCategory = categoryDaoJdbc.find(rs.getInt("product_category"));
                Product temp = new Product(rs.getString("name"), rs.getFloat("default_price"), rs.getString("currency_string"), rs.getString("description"), productCategory, supplier);
                int id = rs.getInt("id");
                temp.setId(id);
                products.add(temp);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try (Connection conn = dataSource.getConnection()){
            SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
            String sql = "SELECT * FROM product WHERE product_category = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productCategory.getId());
            ResultSet rs = ps.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Supplier supplier = supplierDaoJdbc.find(rs.getInt("supplier"));
                Product temp = new Product(rs.getString("name"), rs.getFloat("default_price"), rs.getString("currency_string"), rs.getString("description"), productCategory, supplier);
                int id = rs.getInt("id");
                temp.setId(id);
                products.add(temp);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM product WHERE product_category = ? AND supplier = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productCategory.getId());
            ps.setInt(2, supplier.getId());
            ResultSet rs = ps.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product temp = new Product(rs.getString("name"), rs.getFloat("default_price"), rs.getString("currency_string"), rs.getString("description"), productCategory, supplier);
                int id = rs.getInt("id");
                temp.setId(id);
                products.add(temp);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
