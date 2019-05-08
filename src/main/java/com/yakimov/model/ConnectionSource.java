package com.yakimov.model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionSource {

    private static String url = "jdbc:postgresql://localhost:5432/";
    private static String defaultDatabase = "opossumblog";
    private static String login = "yakimov";
    private static String password = "password";

    private static DataSource dataSource = null;

    public static void connectToDefaultDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url + defaultDatabase);
        ds.setUsername(login);
        ds.setPassword(password);
        ConnectionSource.dataSource = ds;
    }

    public static void connectToDatabase(String database) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url + database);
        ds.setUsername(login);
        ds.setPassword(password);
        ConnectionSource.dataSource = ds;
    }

//    ConnectionSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setUrl(url + defaultDatabase);
//        ds.setUsername(login);
//        ds.setPassword(password);
//        ConnectionSource.dataSource = ds;
//    }
//
//    ConnectionSource(String database) {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setUrl(url + database);
//        ds.setUsername(login);
//        ds.setPassword(password);
//        ConnectionSource.dataSource = ds;
//    }

    public static Connection accuireConnection() throws SQLException {
        if (dataSource == null) {
            connectToDefaultDatabase();
        }
        return dataSource.getConnection();
    }

}
