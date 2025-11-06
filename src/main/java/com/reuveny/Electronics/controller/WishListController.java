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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wish-lists")
@RequiredArgsConstructor
@Tag(
        name = "Wish List Controller",
        description = "Handles all wish list-related endpoints"
)
public class WishListController {
    private final WishListService wishListService;

    @Operation(
            summary = "Get wish list by user ID",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<WishList> getWishList(@PathVariable("userId") Long userId) {
        WishList wishList = wishListService.getWishListByUserId(userId);
        return ResponseEntity.ok(wishList);
    }

    @Operation(
            summary = "Add product to the user's wish list",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    ),
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to add",
                            required = true
                    )
            }
    )
    @PostMapping("/user/{userId}/add-product/{productId}")
    public ResponseEntity<WishList> addProductToWishList(
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId
    ) {
        WishList wishList = wishListService.addProductToWishList(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(wishList);
    }

    @Operation(
            summary = "Remove product from the user's wish list",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    ),
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to remove",
                            required = true
                    )
            }
    )
    @DeleteMapping("/user/{userId}/remove-product/{productId}")
    public ResponseEntity<WishList> removeProductFromWishList(
            @PathVariable("userId") Long userId,
            @PathVariable("productId")
            Long productId
    ) {
        WishList wishList = wishListService.removeProductFromWishList(userId, productId);
        return ResponseEntity.ok(wishList);
    }

    @Operation(
            summary = "Move product from the user's wish list to his shopping cart",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    ),
                    @Parameter(
                            name = "productId",
                            description = "The ID of the product to move",
                            required = true
                    ),
                    @Parameter(
                            name = "quantity",
                            description = "The quantity of the product to move (default is 1)",
                            required = false
                    )
            }
    )
    @PostMapping("/user/{userId}/move-to-cart/{productId}")
    public ResponseEntity<WishList> moveToShoppingCart(
            @PathVariable("userId") Long userId, @PathVariable("productId") Long productId,
            @RequestParam(
                    required = false,
                    defaultValue = "1"
            ) int quantity
    ) {
        WishList updatedWishList = wishListService.moveToShoppingCart(userId, productId, quantity);
        return ResponseEntity.ok(updatedWishList);
    }

    @Operation(
            summary = "Clear the user's wish list",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @PutMapping("user/{userId}/clear-wishlist")
    public ResponseEntity<WishList> clearWishList(@PathVariable("userId") Long userId) {
        WishList wishList = wishListService.clearWishList(userId);
        return ResponseEntity.ok(wishList);
    }
}