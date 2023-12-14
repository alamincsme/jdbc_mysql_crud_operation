package com.jdbc;

import javax.swing.plaf.nimbus.State;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository{
    @Override
    public void save(Product product) {
       var sql = String.format(
               "INSERT INTO product (name , " +
               "description, " +
               "price, " +
               "version, " +
               "datecreated, " +
               "date_last_update)" + "VALUES('%S', '%S','%S','%d','%s','%s')",

               product.getName(),
               product.getDescription(),
               product.getPrice(),
               product.getVersion(),
               Timestamp.valueOf(product.getDateCreated()),
               Timestamp.valueOf(product.getDateLastUpaded()));

       var db = new DBConnection();
       try (var connection = db.tryConnection(); Statement statement = connection.createStatement()) {
           statement.execute(sql);
       } catch (SQLException e) {
           throw new RuntimeException("Unable to insert product information into database.");
       }
    }



    @Override
    public List<Product> findAll() {
        var sql
                = "SELECT * FROM product";

        List<Product> products = new ArrayList<>();

        var dbConnection = new DBConnection();
        try (var connection = dbConnection.tryConnection() ; PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Product product = extractProduct(resultSet);
                    products.add(product);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  products;
    }



    private Product extractProduct(ResultSet resultSet) throws SQLException {
        Product product  = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setVersion(resultSet.getLong("version"));

        var dateCreated = resultSet.getTimestamp("datecreated");
        product.setDateCreated(dateCreated.toLocalDateTime());

        var dateLastUpdated = resultSet.getTimestamp("date_last_update");
        product.setDateLastUpaded(dateLastUpdated.toLocalDateTime());
        return  product;

    }



    @Override
    public void update(Product product) {
        var sql
                = "UPDATE product SET name = ? ," +
                "  description = ?, " +
                "  price = ?, " +
                "  version = ?, " +
                "  date_last_update = ? " +
                "  WHERE id = ? " ;

        DBConnection db = new DBConnection();
        try (var connection = db.tryConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setLong(4, product.getVersion() + 1);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(product.getDateLastUpaded()));
            preparedStatement.setLong(6, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete( ) {
        String sql
                = "DELETE FROM product WHERE id = ?";

        var db = new DBConnection();
        try (var connection = db.tryConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, 3);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Product> productSearchByName(String name) {
        List<Product> productsByName = new ArrayList<>();

        String sql
                = "SELECT * FROM product WHERE name = ?";

        var db = new DBConnection();
        try (var connection = db.tryConnection();PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = new Product();
            while (resultSet.next()) {
                 product = extractProduct(resultSet);
                productsByName.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

     return productsByName;
    }



    public static void main(String[] args) {
        var productRepository = new ProductRepositoryImpl();
        List<Product> productList = productRepository.productSearchByName("Realme Pro");
        System.out.println(productList);


    }
}
