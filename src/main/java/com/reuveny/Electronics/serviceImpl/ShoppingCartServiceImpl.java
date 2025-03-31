/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of the ShoppingCartService to manage shopping cart operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.model.*;
import com.reuveny.Electronics.repository.OrderRepository;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves the shopping cart associated with a user.
     *
     * @param userId The ID of the user.
     * @return The user's shopping cart.
     */
    @Override
    public ShoppingCart getCartByUserId(Long userId) {
        return shoppingCartRepository.findCartByUserId(userId);
    }

    /**
     * Adds a product to the user's shopping cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to add.
     * @param quantity  The quantity of the product to add.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the shopping cart is not initialized or if the product is out of stock.
     */
    @Override
    @Transactional
    public ShoppingCart addProductToCart(Long userId, Long productId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if(shoppingCart == null)
            throw new IllegalArgumentException("Shopping cart must be initialized first.");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product " + productId + " hasn't been found."));

        Optional<Item> existingItem = shoppingCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny();

        if(existingItem.isPresent()) {
            if(product.getStockQuantity() + existingItem.get().getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock: Requested " + quantity +
                        ", but only " + product.getStockQuantity() + " left in stock.");
            }
            product.setStockQuantity(product.getStockQuantity()
                    + existingItem.get().getQuantity() - quantity);
            existingItem.get().setQuantity(quantity);
        } else {
            if(product.getStockQuantity() - quantity < 0) {
                throw new IllegalArgumentException("Insufficient stock: Requested " + quantity +
                        ", but only " + product.getStockQuantity() + " left in stock.");
            }

            Item item = new Item();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);

            shoppingCart.getItems().add(item);

            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        productRepository.save(product);

        double newTotalAmount = 0.0;
        for(Item item: shoppingCart.getItems()) {
            newTotalAmount += item.getProduct().getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);

        return shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Removes a product from the user's shopping cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    @Override
    @Transactional
    public ShoppingCart removeProductFromCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if(shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Shopping cart for user " + userId + " is empty or not found.");
        }

        Optional<Item> itemToRemove = shoppingCart.getItems().stream()
                .filter((item) -> item.getProduct().getId().equals(productId))
                .findFirst();

        itemToRemove.ifPresent((item) -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity()
                    + item.getQuantity());
            productRepository.save(product);

            shoppingCart.getItems().remove(item);
        });


        double newTotalAmount = 0.0;
        for(Item item: shoppingCart.getItems()) {
            newTotalAmount += item.getProduct().getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);

        return shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Clears all products from the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return The updated empty shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    @Override
    @Transactional
    public ShoppingCart clearCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if(shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Shopping cart for user " + userId + " is empty or not found.");
        }

        shoppingCart.getItems().forEach((item -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity()
                    + item.getQuantity());
            productRepository.save(product);
        }));

        shoppingCart.getItems().clear();

        shoppingCart.setTotalAmount(0.0);

        return shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Processes the checkout operation, creating an order from the shopping cart.
     *
     * @param userId The ID of the user.
     * @return The created order.
     * @throws IllegalStateException if the cart is empty.
     */
    @Override
    @Transactional
    public Order checkout(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty. Add items before checkout.");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(shoppingCart.getTotalAmount());
        order.setStatus(Status.PENDING);
        order.setUser(shoppingCart.getUser());

        List<Item> orderItems = new ArrayList<>();

        for (Item cartItem : shoppingCart.getItems()) {
            Item orderItem = new Item();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        shoppingCart.getItems().clear();

        shoppingCart.setTotalAmount(0.0);

        shoppingCartRepository.save(shoppingCart);

        return orderRepository.save(order);
    }
}