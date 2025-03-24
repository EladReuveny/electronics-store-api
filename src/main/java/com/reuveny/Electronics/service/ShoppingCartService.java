/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getCartByUserId(Long userId);
    ShoppingCart addProductToCart(Long userId, Long productId, int quantity);
    ShoppingCart removeProductFromCart(Long userId, Long productId);
    ShoppingCart clearCart(Long userId);
    Order checkout(Long userId);
}
