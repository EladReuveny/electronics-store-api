/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Implementation of the WishListService interface to handle wishlist-related operations.
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

    /**
     * Retrieves the wishlist associated with a specific user.
     *
     * @param userId the ID of the user whose wishlist is to be retrieved
     * @return the wishlist of the user
     * @throws IllegalArgumentException if no wishlist is found for the given user
     */
    @Override
    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findWishListByUserId(userId);
    }

    /**
     * Adds a product to the user's wishlist.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be added
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist or product is not found or if the product already exists in the wishlist
     */
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

    /**
     * Removes a product from the user's wishlist.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be removed
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist is empty or the product does not exist in the wishlist
     */
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

    /**
     * Moves a product from the wishlist to the shopping cart, updating stock and quantities.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to be moved
     * @param quantity the quantity of the product to be moved
     * @return the updated wishlist
     * @throws IllegalArgumentException if the wishlist, product, or shopping cart is not found, or if there is insufficient stock
     */
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
            if(product.getStockQuantity() + existingItem.get().getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock: Requested " + quantity +
                        ", but only " + product.getStockQuantity() + " left in stock.");
            }

            product.setStockQuantity(product.getStockQuantity()
                    + existingItem.get().getQuantity() - quantity);
            existingItem.get().setQuantity(quantity);
        } else {
            if(product.getStockQuantity() - quantity < 0) {
                throw new IllegalArgumentException("Insufficient stock: Requested " + quantity +
                        ", but only " + product.getStockQuantity() + " left in stock.");
            }

            Item item = new Item();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);

            shoppingCart.getItems().add(item);

            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        productRepository.save(product);

        double newTotalAmount = 0.0;
        for(Item item: shoppingCart.getItems()) {
            newTotalAmount += item.getProduct().getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);

        shoppingCartRepository.save(shoppingCart);

        wishList.getProducts().remove(product);

        return wishList;
    }

    /**
     * Clears all products from the user's wishlist.
     *
     * @param userId the ID of the user
     * @return the cleared wishlist
     * @throws IllegalArgumentException if the wishlist is empty or not found
     */
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