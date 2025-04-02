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
     * Retrieves the wishlist associated with a specific user.
     *
     * @param userId the ID of the user whose wishlist is to be retrieved
     * @return the wishlist of the user
     * @throws IllegalArgumentException if no wishlist is found for the given user
     */
    WishList getWishListByUserId(Long userId);

    /**
     * Adds a product to the user's wishlist.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be added
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist or product is not found or if the product already exists in the wishlist
     */
    WishList addProductToWishList(Long userId, Long productId);

    /**
     * Removes a product from the user's wishlist.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be removed
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist is empty or the product does not exist in the wishlist
     */
    WishList removeProductFromWishList(Long userId, Long productId);

    /**
     * Moves a product from the wishlist to the shopping cart, updating stock and quantities.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be moved
     * @param quantity the quantity of the product to be moved
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist, product, or shopping cart is not found, or if there is insufficient stock
     */
    WishList moveToShoppingCart(Long userId, Long productId, int quantity);

    /**
     * Clears all products from the user's wishlist.
     *
     * @param userId the ID of the user
     * @return the cleared wishlist
     * @throws IllegalArgumentException if the wishlist is empty or not found
     */
    WishList clearWishList(Long userId);
}

