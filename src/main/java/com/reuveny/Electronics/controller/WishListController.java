/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wish-list")
@CrossOrigin(origins = "http://localhost:5173")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @GetMapping("/user/{userId}")
    public WishList getWishList(@PathVariable("userId") Long userId) {
        return wishListService.getWishListByUserId(userId);
    }

    @PostMapping("/user/{userId}/add-product/{productId}")
    public WishList addProductToWishList(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return wishListService.addProductToWishList(userId, productId);
    }

    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public WishList removeProductFromWishList(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId) {
        return wishListService.removeProductFromWishList(userId, productId);
    }

    @PostMapping("/user/{userId}/move-to-cart/{productId}")
    public WishList moveToShoppingCart(@PathVariable("userId") Long userId
            , @PathVariable("productId") Long productId
            , @RequestParam(required = false, defaultValue = "1") int quantity) {
        return wishListService.moveToShoppingCart(userId, productId, quantity);
    }

    @PutMapping("user/{userId}/clear-wishlist")
    public WishList clearWishList(@PathVariable("userId") Long userId) {
        return wishListService.clearWishList(userId);
    }
}
