/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service interface for managing products.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;

import java.util.List;

public interface ProductService {
    /**
     * Fetch a product by its ID.
     *
     * @param productId The product ID.
     * @return The product if found.
     */
    Product getProductById(Long productId);

    /**
     * Retrieve all products.
     *
     * @return List of all products.
     */
    List<Product> getAllProducts();

    /**
     * Search for products by name.
     *
     * @param name The search query.
     * @return List of products matching the search.
     */
    List<Product> searchProductsByName(String name);

    /**
     * Retrieve products by category.
     *
     * @param category The category of products.
     * @return List of products in the given category.
     */
    List<Product> getProductsByCategory(Category category);

    /**
     * Add a new product.
     *
     * @param product The product to be added.
     * @return The created product.
     */
    Product addProduct(Product product);

    /**
     * Update an existing product.
     *
     * @param productId The ID of the product to update.
     * @param product   The updated product details.
     * @return The updated product.
     */
    Product updateProduct(Long productId, Product product);

    /**
     * Delete a product by its ID.
     *
     * @param productId The product ID.
     */
    void deleteProduct(Long productId);
}
