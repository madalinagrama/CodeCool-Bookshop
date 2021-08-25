package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DatabaseManager {
    private CartDao cartDao;
    private LineItemDao lineItemDao;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private Dotenv dotenv;
    private static Object dao;

    public static Object getDao() {
        return dao;
    }

    public DataSource setup() throws SQLException {
        DataSource dataSource = connect();
//        lineItemDao = new LineItemJdbc(dataSource);
//        cartDao= new CartJdbc(dataSource, lineItemDao);
//        productDao = new ProductDaoJdbc(dataSource);
//        categoryDao = new CategoryDaoJdbc(dataSource);
//        supplierDao = new SupplierDaoJdbc(dataSource);
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
