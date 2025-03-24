/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/user/{userId}")
    public ShoppingCart getCartByUserId(@PathVariable("userId") Long userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @PostMapping("/user/{userId}/add-product/{productId}")
    public ShoppingCart addProductToCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId
            , @RequestParam(required = false, defaultValue = "1") int quantity) {
        return shoppingCartService.addProductToCart(userId, productId, quantity);
    }

    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public ShoppingCart removeProductFromCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return shoppingCartService.removeProductFromCart(userId, productId);
    }

    @PutMapping("/user/{userId}/clear-cart")
    public ShoppingCart clearCart(@PathVariable("userId") Long userId) {
        return shoppingCartService.clearCart(userId);
    }

    @PostMapping("/user/{userId}/checkout")
    public Order checkout(@PathVariable("userId") Long userId) {
        return shoppingCartService.checkout(userId);
    }
}
