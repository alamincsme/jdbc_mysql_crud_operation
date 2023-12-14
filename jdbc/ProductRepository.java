package com.jdbc;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
    List<Product> findAll() ;
    void update(Product product);
    void delete( ) ;

    List<Product> productSearchByName (String name) ;
}
