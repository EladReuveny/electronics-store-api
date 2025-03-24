/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.WishList;

public interface WishListService {
    WishList getWishListByUserId(Long userId);
    WishList addProductToWishList(Long userId, Long productId);
    WishList removeProductFromWishList(Long userId, Long productId);
    WishList moveToShoppingCart(Long userId, Long productId, int quantity);
    WishList clearWishList(Long userId);
}

