/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("query") String name) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable("category") Category category) {
        return productService.getProductsByCategory(category);
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{productId}")
    public void deleteProductById(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
    }
}
