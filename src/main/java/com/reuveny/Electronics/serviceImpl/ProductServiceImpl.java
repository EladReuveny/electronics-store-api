/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of ProductService to manage product-related operations.
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

    @Override
    public Product getProductById(Long productId) throws IllegalArgumentException {
        return productRepository.
                findById(productId).
                orElseThrow(() -> new IllegalArgumentException("Product hasn't been found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

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

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}