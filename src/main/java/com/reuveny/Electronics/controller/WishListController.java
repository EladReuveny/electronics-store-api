/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for handling wish list operations, allowing users to
 * add, remove, retrieve, and move products from the wish list.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wish-list")
@CrossOrigin(origins = "http://localhost:5173")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    /**
     * Retrieves the wish list for a specific user.
     *
     * @param userId The ID of the user.
     * @return The user's wish list.
     */
    @GetMapping("/user/{userId}")
    public WishList getWishList(@PathVariable("userId") Long userId) {
        return wishListService.getWishListByUserId(userId);
    }

    /**
     * Adds a product to the user's wish list.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to add.
     * @return The updated wish list.
     */
    @PostMapping("/user/{userId}/add-product/{productId}")
    public WishList addProductToWishList(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return wishListService.addProductToWishList(userId, productId);
    }

    /**
     * Removes a product from the user's wish list.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The updated wish list.
     */
    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public WishList removeProductFromWishList(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return wishListService.removeProductFromWishList(userId, productId);
    }

    /**
     * Moves a product from the user's wish list to the shopping cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to move.
     * @param quantity  The quantity of the product to move (default is 1).
     * @return A response entity containing the updated wish list or an error message.
     */
    @PostMapping("/user/{userId}/move-to-cart/{productId}")
    public ResponseEntity<?> moveToShoppingCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId
            , @RequestParam(required = false, defaultValue = "1") int quantity) {
        try {
            WishList updatedWishList = wishListService.moveToShoppingCart(userId, productId, quantity);
            return ResponseEntity.ok(updatedWishList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Clears the user's wish list.
     *
     * @param userId The ID of the user.
     * @return The updated (empty) wish list.
     */
    @PutMapping("user/{userId}/clear-wishlist")
    public WishList clearWishList(@PathVariable("userId") Long userId) {
        return wishListService.clearWishList(userId);
    }
}