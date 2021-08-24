package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DatabaseManager {
    private CartDao cartDao;
    private LineItemDao lineItemDao;
    private ProductCategoryDao productCategoryDao;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private Dotenv dotenv;

    public DataSource setup() throws SQLException {
        DataSource dataSource = connect();
        lineItemDao = new LineItemJdbc(dataSource);
//        TODO check if we need also lineItem as parameter
        cartDao= new CartJdbc(dataSource);
        productDao = new ProductDaoJdbc(dataSource);
//        TODO check if we need also product as parameter
        productCategoryDao = new ProductCategoryDaoJdbc(dataSource);
//        TODO check if we need also product as parameter
        supplierDao = new SupplierDaoJdbc(dataSource);
        return dataSource;
    }

    private DataSource connect() throws SQLException {
        dotenv = Dotenv.load();

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = dotenv.get("PSQL_DB_NAME");
        String user = dotenv.get("PSQL_USER_NAME");
        String password = dotenv.get("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
