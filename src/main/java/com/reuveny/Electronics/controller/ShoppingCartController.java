/**
 * @package Electronics
 * @author Elad Reuveny
 *
 *  Controller for managing shopping cart operations.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Retrieves the shopping cart for a given user.
     *
     * @param userId The ID of the user.
     * @return The shopping cart associated with the user.
     */
    @GetMapping("/user/{userId}")
    public ShoppingCart getCartByUserId(@PathVariable("userId") Long userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product.
     * @param quantity The quantity of the product to add (default is 1).
     * @return The updated shopping cart.
     */
    @PostMapping("/user/{userId}/add-product/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId
            , @RequestParam(required = false, defaultValue = "1") int quantity) {
        try {
            ShoppingCart updatedShoppingCart = shoppingCartService.addProductToCart(userId, productId, quantity);
            return ResponseEntity.ok(updatedShoppingCart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Removes a product from the shopping cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product.
     * @return The updated shopping cart.
     */
    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public ShoppingCart removeProductFromCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return shoppingCartService.removeProductFromCart(userId, productId);
    }

    /**
     * Clears all items from the shopping cart.
     *
     * @param userId The ID of the user.
     * @return The updated (empty) shopping cart.
     */
    @PutMapping("/user/{userId}/clear-cart")
    public ShoppingCart clearCart(@PathVariable("userId") Long userId) {
        return shoppingCartService.clearCart(userId);
    }

    /**
     * Proceeds to checkout and places an order.
     *
     * @param userId The ID of the user.
     * @return The created order.
     */
    @PostMapping("/user/{userId}/checkout")
    public Order checkout(@PathVariable("userId") Long userId) {
        return shoppingCartService.checkout(userId);
    }
}