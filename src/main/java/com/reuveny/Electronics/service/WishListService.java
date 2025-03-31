/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Manages wishlist operations, such as adding and removing products from a user's wishlist.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.WishList;

public interface WishListService {
    /**
     * Retrieves the wish list for a given user.
     *
     * @param userId The ID of the user.
     * @return The wish list associated with the user.
     * @throws IllegalArgumentException if no wishlist is found for the given user
     */
    WishList getWishListByUserId(Long userId);

    /**
     * Adds a product to the user's wish list.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to add.
     * @return The updated wish list.
     * @throws IllegalArgumentException if the wishlist or product is not found or if the product already exists in the wishlist
     */
    WishList addProductToWishList(Long userId, Long productId);

    /**
     * Removes a product from the user's wish list.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The updated wish list.
     * @throws IllegalArgumentException if the wishlist is empty or the product does not exist in the wishlist
     */
    WishList removeProductFromWishList(Long userId, Long productId);

    /**
     * Moves a product from the wish list to the shopping cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to move.
     * @param quantity  The quantity of the product to move.
     * @return The updated wish list after removing the product.
     * @throws IllegalArgumentException if the wishlist, product, or shopping cart is not found, or if there is insufficient stock
     */
    WishList moveToShoppingCart(Long userId, Long productId, int quantity);

    /**
     * Clears all products from the user's wish list.
     *
     * @param userId The ID of the user.
     * @return The empty wish list.
     * @throws IllegalArgumentException if the wishlist is empty or not found
     */
    WishList clearWishList(Long userId);
}

