/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.model.*;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.repository.WishListRepository;
import com.reuveny.Electronics.service.WishListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findWishListByUserId(userId);
    }

    @Override
    @Transactional
    public WishList addProductToWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository
                .findWishListByUserId(userId);
        if(wishList == null) {
            throw new IllegalArgumentException("Wishlist " + userId +" hasn't been found.");
        }

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product " + productId + " hasn't been found."));

        List<Product> productsList = wishList.getProducts();
        if(!productsList.contains(product)) {
            productsList.add(product);
        } else {
            throw new IllegalArgumentException("Product " + productId + " is already exist in the wishlist.");
        }
        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public WishList removeProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository
                .findWishListByUserId(userId);
        if(wishList == null || wishList.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Wishlist " + userId +" hasn't been found.");
        }

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product " + productId + " hasn't been found."));

        List<Product> productsList = wishList.getProducts();
        if(productsList.contains(product)) {
            productsList.remove(product);
        } else {
            throw new IllegalArgumentException("Product " + productId + " isn't existing in the wishlist.");
        }
        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public WishList moveToShoppingCart(Long userId, Long productId, int quantity) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if(wishList == null || wishList.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Wishlist " + userId +" hasn't been found.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product hasn't been found."));

        ShoppingCart shoppingCart = wishList.getUser().getShoppingCart();

        Optional<Item> existingItem = shoppingCart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(quantity);
        } else {
            Item item = new Item();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);

            shoppingCart.getItems().add(item);
        }

        double newTotalAmount = 0.0;
        for(Item item: shoppingCart.getItems()) {
            newTotalAmount += item.getProduct().getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);

        shoppingCartRepository.save(shoppingCart);

        wishList.getProducts().remove(product);

        return wishList;
    }

    @Override
    @Transactional
    public WishList clearWishList(Long userId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if(wishList == null || wishList.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Wish list for user " + userId + " is empty or not found.");
        }

        wishList.getProducts().clear();

        return wishListRepository.save(wishList);
    }
}
