/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Manages product-related operations like retrieving, adding, updating, and deleting products.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;

import java.util.List;

public interface ProductService {
    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product.
     * @return The product if found.
     * @throws IllegalArgumentException if the product is not found.
     */
    Product getProductById(Long productId);

    /**
     * Retrieves all available products.
     *
     * @return A list of all products.
     */
    List<Product> getAllProducts();

    /**
     * Searches for products by name (case-insensitive and partial match).
     *
     * @param name The product name to search for.
     * @return A list of matching products.
     */
    List<Product> searchProductsByName(String name);

    /**
     * Retrieves products by a specific category.
     *
     * @param category The category of products.
     * @return A list of products in the given category.
     */
    List<Product> getProductsByCategory(Category category);

    /**
     * Adds a new product to the database.
     *
     * @param product The product to add.
     * @return The saved product.
     */
    Product addProduct(Product product);

    /**
     * Updates an existing product's details.
     * Only non-null fields in the provided product object will be updated.
     *
     * @param productId The ID of the product to update.
     * @param product   The product object containing updated details.
     * @return The updated product.
     * @throws IllegalArgumentException if the product is not found.
     */
    Product updateProduct(Long productId, Product product);

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    void deleteProduct(Long productId);
}
