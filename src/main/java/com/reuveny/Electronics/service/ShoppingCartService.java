/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Manages shopping cart operations, such as adding/removing items and calculating totals.
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
     * @throws IllegalArgumentException if the shopping cart is not initialized or if the product is out of stock.
     */
    ShoppingCart addProductToCart(Long userId, Long productId, int quantity);

    /**
     * Removes a product from the shopping cart.
     *
     * @param userId The user ID.
     * @param productId The product ID.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    ShoppingCart removeProductFromCart(Long userId, Long productId);

    /**
     * Clears all items from the shopping cart.
     *
     * @param userId The user ID.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    ShoppingCart clearCart(Long userId);

    /**
     * Processes checkout and creates an order.
     *
     * @param userId The user ID.
     * @return The created order.
     * @throws IllegalStateException if the cart is empty.
     */
    Order checkout(Long userId);
}
