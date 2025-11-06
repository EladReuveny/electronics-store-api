/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing products in the Electronics store.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.dto.ProductUpdateDto;
import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(
        name = "Product Controller",
        description = "Handles all product-related endpoints"
)
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Get product by ID",
            parameters = {
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to retrieve",
                            required = true
                    )
            }
    )
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Get all products")
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Search products by name",
            parameters = {
                    @Parameter(
                            name = "query",
                            description = "The name or partial name of the product to search for",
                            required = true
                    )
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam("query") String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Get products by category",
            parameters = {
                    @Parameter(
                            name = "category",
                            description = "The category to filter products by",
                            required = true
                    )
            }
    )
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable("category") Category category
    ) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Add a new product",
            parameters = {
                    @Parameter(
                            name = "product",
                            description = "The product to add",
                            required = true
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(addedProduct);
    }

    @Operation(
            summary = "Update a product",
            parameters = {
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to update",
                            required = true
                    ),
                    @Parameter(
                            name = "productUpdateDTO",
                            description = "The product object containing updated details",
                            required = true
                    )
            }
    )
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("productId") Long id,
            @RequestBody ProductUpdateDto productUpdateDTO
    ) {
        Product updatedProduct = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(
            summary = "Delete a product",
            parameters = {
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to delete",
                            required = true
                    )
            }
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent()
                             .build();
    }

    @Operation(
            summary = "Remove selected products by their IDs",
            parameters = {
                    @Parameter(
                            name = "productIds",
                            description = "A list of product IDs to be deleted",
                            required = true
                    )
            }
    )
    @PutMapping("/remove-selected-products")
    public ResponseEntity<String> removeSelectedProducts(@RequestBody List<Long> productIds) {
        productService.removeSelectedProducts(productIds);
        return ResponseEntity.ok("Selected products deleted successfully.");
    }
}