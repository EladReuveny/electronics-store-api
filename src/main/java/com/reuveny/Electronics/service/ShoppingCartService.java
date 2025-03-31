/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service interface for managing shopping cart operations.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.ShoppingCart;

public interface ShoppingCartService {
    /**
     * Retrieves the shopping cart for a user.
     *
     * @param userId The user ID.
     * @return The user's shopping cart.
     */
    ShoppingCart getCartByUserId(Long userId);

    /**
     * Adds a product to the shopping cart.
     *
     * @param userId The user ID.
     * @param productId The product ID.
     * @param quantity The quantity of the product.
     * @return The updated shopping cart.
     */
    ShoppingCart addProductToCart(Long userId, Long productId, int quantity);

    /**
     * Removes a product from the shopping cart.
     *
     * @param userId The user ID.
     * @param productId The product ID.
     * @return The updated shopping cart.
     */
    ShoppingCart removeProductFromCart(Long userId, Long productId);

    /**
     * Clears all items from the shopping cart.
     *
     * @param userId The user ID.
     * @return The updated shopping cart.
     */
    ShoppingCart clearCart(Long userId);

    /**
     * Processes checkout and creates an order.
     *
     * @param userId The user ID.
     * @return The created order.
     */
    Order checkout(Long userId);
}
