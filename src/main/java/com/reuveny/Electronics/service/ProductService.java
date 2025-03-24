/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    List<Product> searchProductsByName(String name);
    List<Product> getProductsByCategory(Category category);
    Product addProduct(Product product);
    Product updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
}
