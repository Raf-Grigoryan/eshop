package eshop.service;

import eshop.model.Product;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    List<Product> getProducts();

    Product getProductById(int id);

    Product getProductWithMaxPrice();

    Product getProductWithMinPrice();

    void removeProduct(int id);

    int productCount();

    int averagePrice();
}
