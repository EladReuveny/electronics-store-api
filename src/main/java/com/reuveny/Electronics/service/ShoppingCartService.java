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
     * Retrieves the shopping cart associated with a user.
     *
     * @param userId The ID of the user.
     * @return The user's shopping cart.
     */
    ShoppingCart getCartByUserId(Long userId);

    /**
     * Adds a product to the user's shopping cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to add.
     * @param quantity  The quantity of the product to add.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the shopping cart is not initialized or if the product is out of stock.
     */
    ShoppingCart addProductToCart(Long userId, Long productId, int quantity);

    /**
     * Removes a product from the user's shopping cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The updated shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    ShoppingCart removeProductFromCart(Long userId, Long productId);

    /**
     * Clears all products from the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return The updated empty shopping cart.
     * @throws IllegalArgumentException if the cart is empty or not found.
     */
    ShoppingCart clearCart(Long userId);

    /**
     * Processes the checkout operation, creating an order from the shopping cart.
     *
     * @param userId The ID of the user.
     * @return The created order.
     * @throws IllegalStateException if the cart is empty.
     */
    Order checkout(Long userId);
}
