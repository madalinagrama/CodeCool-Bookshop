package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DatabaseManager {
    private Dotenv dotenv;


    public DataSource setup() throws SQLException {
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
