/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing shopping cart operations.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
        name = "Shopping Cart Controller",
        description = "Handles all shopping cart-related endpoints"
)
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(
            summary = "Get shopping cart by user ID",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<ShoppingCart> getCartByUserId(
            @PathVariable("userId") Long userId
    ) {
        ShoppingCart shoppingCart = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(shoppingCart);
    }

    @Operation(
            summary = "Add product to shopping cart",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    ),
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product",
                            required = true
                    ),
                    @Parameter(
                            name = "quantity",
                            description = "The quantity of the product to add (default is 1)",
                            required = false
                    )
            }
    )
    @PostMapping("/user/{userId}/add-product/{productId}")
    public ResponseEntity<ShoppingCart> addProductToCart(
            @PathVariable("userId") Long userId, @PathVariable("productId") Long productId,
            @RequestParam(
                    required = false,
                    defaultValue = "1"
            ) int quantity
    ) {
        ShoppingCart updatedShoppingCart =
                shoppingCartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(updatedShoppingCart);
    }

    @Operation(
            summary = "Remove product from shopping cart",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    ),
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product",
                            required = true
                    )
            }
    )
    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public ResponseEntity<ShoppingCart> removeProductFromCart(
            @PathVariable("userId") Long userId,
            @PathVariable("productId")
            Long productId
    ) {
        ShoppingCart updatedShoppingCart =
                shoppingCartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @Operation(
            summary = "Clear shopping cart",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @PutMapping("/user/{userId}/clear-cart")
    public ResponseEntity<ShoppingCart> clearCart(
            @PathVariable("userId") Long userId
    ) {
        ShoppingCart updatedShoppingCart = shoppingCartService.clearCart(userId);
        return ResponseEntity.ok(updatedShoppingCart);
    }

    @Operation(
            summary = "Checkout and place an order",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @PostMapping("/user/{userId}/checkout")
    public ResponseEntity<Order> checkout(
            @PathVariable("userId") Long userId
    ) {
        Order order = shoppingCartService.checkout(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(order);
    }
}