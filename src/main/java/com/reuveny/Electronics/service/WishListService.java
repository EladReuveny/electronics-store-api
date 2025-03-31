/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service interface defining operations for managing a user's wish list.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.WishList;

public interface WishListService {
    /**
     * Retrieves the wish list for a given user.
     *
     * @param userId The ID of the user.
     * @return The wish list associated with the user.
     */
    WishList getWishListByUserId(Long userId);

    /**
     * Adds a product to the user's wish list.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to add.
     * @return The updated wish list.
     */
    WishList addProductToWishList(Long userId, Long productId);

    /**
     * Removes a product from the user's wish list.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The updated wish list.
     */
    WishList removeProductFromWishList(Long userId, Long productId);

    /**
     * Moves a product from the wish list to the shopping cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to move.
     * @param quantity  The quantity of the product to move.
     * @return The updated wish list after removing the product.
     */
    WishList moveToShoppingCart(Long userId, Long productId, int quantity);

    /**
     * Clears all products from the user's wish list.
     *
     * @param userId The ID of the user.
     * @return The empty wish list.
     */
    WishList clearWishList(Long userId);
}

