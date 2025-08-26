/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of the ShoppingCartService to manage shopping cart operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.exception.ResourceNotFoundException;
import com.reuveny.Electronics.model.*;
import com.reuveny.Electronics.repository.OrderRepository;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    @Override
    public ShoppingCart getCartByUserId(Long userId) {
        return shoppingCartRepository.findCartByUserId(userId);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToCart(Long userId, Long productId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if (shoppingCart == null)
            throw new IllegalArgumentException("Shopping cart must be initialized first.");
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product " + productId + " hasn't been found."));
        Optional<Item> existingItem = shoppingCart.getItems()
                                                  .stream()
                                                  .filter(item -> item.getProduct()
                                                                      .getId()
                                                                      .equals(productId))
                                                  .findAny()
                ;
        if (existingItem.isPresent()) {
            if (product.getStockQuantity() + existingItem.get()
                                                         .getQuantity() < quantity) {
                throw new IllegalArgumentException(
                        "Insufficient stock: Requested " + quantity + ", but only " +
                        product.getStockQuantity() + " left in stock.");
            }
            product.setStockQuantity(product.getStockQuantity() + existingItem.get()
                                                                              .getQuantity() -
                                     quantity);
            existingItem.get()
                        .setQuantity(quantity);
        } else {
            if (product.getStockQuantity() - quantity < 0) {
                throw new IllegalArgumentException(
                        "Insufficient stock: Requested " + quantity + ", but only " +
                        product.getStockQuantity() + " left in stock.");
            }
            Item item = new Item();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);
            shoppingCart.getItems()
                        .add(item);
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        productRepository.save(product);
        double newTotalAmount = 0.0;
        for (Item item : shoppingCart.getItems()) {
            newTotalAmount += item.getProduct()
                                  .getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart removeProductFromCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if (shoppingCart == null || shoppingCart.getItems()
                                                .isEmpty()) {
            throw new IllegalArgumentException(
                    "Shopping cart for user " + userId + " is empty or not found.");
        }
        Optional<Item> itemToRemove = shoppingCart.getItems()
                                                  .stream()
                                                  .filter((item) -> item.getProduct()
                                                                        .getId()
                                                                        .equals(productId))
                                                  .findFirst()
                ;
        itemToRemove.ifPresent((item) -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
            shoppingCart.getItems()
                        .remove(item);
        });
        double newTotalAmount = 0.0;
        for (Item item : shoppingCart.getItems()) {
            newTotalAmount += item.getProduct()
                                  .getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart clearCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if (shoppingCart == null || shoppingCart.getItems()
                                                .isEmpty()) {
            throw new IllegalArgumentException(
                    "Shopping cart for user " + userId + " is empty or not found.");
        }
        shoppingCart.getItems()
                    .forEach((
                                     item -> {
                                         Product product = item.getProduct();
                                         product.setStockQuantity(
                                                 product.getStockQuantity() + item.getQuantity());
                                         productRepository.save(product);
                                     }
                             ));
        shoppingCart.getItems()
                    .clear();
        shoppingCart.setTotalAmount(0.0);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public Order checkout(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId);
        if (shoppingCart == null || shoppingCart.getItems()
                                                .isEmpty()) {
            throw new IllegalArgumentException(
                    "Shopping cart is empty. Add items before checkout.");
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
        shoppingCart.getItems()
                    .clear();
        shoppingCart.setTotalAmount(0.0);
        shoppingCartRepository.save(shoppingCart);
        return orderRepository.save(order);
    }
}