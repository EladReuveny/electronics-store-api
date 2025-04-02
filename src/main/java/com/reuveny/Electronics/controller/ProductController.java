/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing products in the Electronics store.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Fetch a product by its ID.
     *
     * @param id The product ID.
     * @return The product if found.
     */
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") Long id) {
        return productService.getProductById(id);
    }

    /**
     * Retrieve all products.
     *
     * @return List of all products.
     */
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Search for products by name.
     *
     * @param name The search query.
     * @return List of products matching the search.
     */
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("query") String name) {
        return productService.searchProductsByName(name);
    }

    /**
     * Fetch products by category.
     *
     * @param category The product category.
     * @return List of products in the category.
     */
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable("category") Category category) {
        return productService.getProductsByCategory(category);
    }

    /**
     * Add a new product.
     *
     * @param product The product details in the request body.
     * @return The created product or an error message if validation fails.
     */
    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok(addedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Update an existing product.
     *
     * @param id The product ID.
     * @param product The updated product details.
     * @return The updated product.
     */
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    /**
     * Delete a product by its ID.
     *
     * @param id The product ID.
     */
    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
    }

    /**
     * Remove selected products.
     *
     * @param productIds A list of product IDs to be removed.
     * @return A response message confirming deletion.
     */
    @PutMapping("/remove-selected-products")
    public ResponseEntity<String> removeSelectedProducts(@RequestBody List<Long> productIds) {
        productService.removeSelectedProducts(productIds);
        return ResponseEntity.ok("Selected products deleted successfully.");
    }
}