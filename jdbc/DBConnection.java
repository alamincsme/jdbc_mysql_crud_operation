package com.jdbc;



import java.sql.*;

public  class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/eShoppers";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "alamincsme";

    public Connection tryConnection () throws SQLException {
        return DriverManager.getConnection(URL, USERNAME,PASSWORD);
    }

}
class createDatabase {
    public void createDatabase (Connection connection, String dbName) {
        String sql = "create database " + dbName;

        try {
            var statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("unable to connect.");
        }
    }


}

class CreateTable {
    public void createTable(Connection connection) {

        String ddl
                = "Create table product" +
                "(" +
                "   id bigint auto_increment primary key , " +
                "   name varchar(100) not null," +
                "   description varchar(500) null , " +
                "   price decimal not null," +
                "   version bigint not null, " +
                "   datecreated timestamp not null, " +
                "   date_last_update timestamp null" +
                ");";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(ddl);

            String sql = "show tables like 'product'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                System.out.println("Table successfully created.");
            } else {
                System.out.println("Table created failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException("unable to close");
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException("unable to close connection.");
                }
            }
        }

    }

}

