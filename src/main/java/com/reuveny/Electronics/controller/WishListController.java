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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wish-list")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
        name = "Wish List Controller",
        description = "Handles all wish list-related endpoints"
)
public class WishListController {
    @Autowired
    private WishListService wishListService;

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
    public WishList getWishList(@PathVariable("userId") Long userId) {
        return wishListService.getWishListByUserId(userId);
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
    public WishList addProductToWishList(
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId
    ) {
        return wishListService.addProductToWishList(userId, productId);
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
    public WishList removeProductFromWishList(
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId
    ) {
        return wishListService.removeProductFromWishList(userId, productId);
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
    public ResponseEntity<?> moveToShoppingCart(
            @PathVariable("userId") Long userId, @PathVariable("productId") Long productId,
            @RequestParam(
                    required = false,
                    defaultValue = "1"
            ) int quantity
    ) {
        try {
            WishList updatedWishList =
                    wishListService.moveToShoppingCart(userId, productId, quantity);
            return ResponseEntity.ok(updatedWishList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
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
    public WishList clearWishList(@PathVariable("userId") Long userId) {
        return wishListService.clearWishList(userId);
    }
}