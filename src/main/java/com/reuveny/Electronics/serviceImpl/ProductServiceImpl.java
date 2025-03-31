/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Implementation of ProductService to manage product-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product.
     * @return The product if found.
     * @throws IllegalArgumentException if the product is not found.
     */
    @Override
    public Product getProductById(Long productId) throws IllegalArgumentException {
        return productRepository.
                findById(productId).
                orElseThrow(() -> new IllegalArgumentException("Product hasn't been found"));
    }

    /**
     * Retrieves all available products.
     *
     * @return A list of all products.
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Searches for products by name (case-insensitive and partial match).
     *
     * @param name The product name to search for.
     * @return A list of matching products.
     */
    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameIgnoreCaseContaining(name);
    }

    /**
     * Retrieves products by a specific category.
     *
     * @param category The category of products.
     * @return A list of products in the given category.
     */
    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }

    /**
     * Adds a new product to the database.
     *
     * @param product The product to add.
     * @return The saved product.
     */
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product's details.
     * Only non-null fields in the provided product object will be updated.
     *
     * @param productId The ID of the product to update.
     * @param product   The product object containing updated details.
     * @return The updated product.
     * @throws IllegalArgumentException if the product is not found.
     */
    @Override
    @Transactional
    public Product updateProduct(Long productId, Product product) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    if (product.getName() != null) {
                        existingProduct.setName(product.getName());
                    }
                    if (product.getDescription() != null) {
                        existingProduct.setDescription(product.getDescription());
                    }
                    if (product.getPrice() != null) {
                        existingProduct.setPrice(product.getPrice());
                    }
                    if (product.getImgUrl() != null) {
                        existingProduct.setImgUrl(product.getImgUrl());
                    }
                    if (product.getStockQuantity() != null) {
                        existingProduct.setStockQuantity(product.getStockQuantity());
                    }
                    if (product.getCategory() != null) {
                        existingProduct.setCategory(product.getCategory());
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product hasn't been found"));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}