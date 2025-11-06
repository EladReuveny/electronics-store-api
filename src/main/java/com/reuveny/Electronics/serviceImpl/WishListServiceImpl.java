/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of the WishListService interface to handle wishlist-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.exception.ResourceAlreadyExistsException;
import com.reuveny.Electronics.exception.ResourceNotFoundException;
import com.reuveny.Electronics.model.Item;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.repository.WishListRepository;
import com.reuveny.Electronics.service.WishListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;

    private final ProductRepository productRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public WishList getWishListByUserId(Long userId) {
        return wishListRepository.findWishListByUserId(userId);
    }

    @Override
    @Transactional
    public WishList addProductToWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if (wishList == null) {
            throw new ResourceNotFoundException("Wishlist " + userId + " hasn't been found.");
        }
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product " + productId + " hasn't been found."));
        List<Product> productsList = wishList.getProducts();
        if (!productsList.contains(product)) {
            productsList.add(product);
        } else {
            throw new ResourceAlreadyExistsException(
                    "Product " + productId + " is already exist in the wishlist.");
        }
        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public WishList removeProductFromWishList(Long userId, Long productId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if (wishList == null || wishList.getProducts()
                                        .isEmpty()) {
            throw new ResourceNotFoundException("Wishlist " + userId + " hasn't been found.");
        }
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product " + productId + " hasn't been found."));
        List<Product> productsList = wishList.getProducts();
        if (productsList.contains(product)) {
            productsList.remove(product);
        } else {
            throw new IllegalArgumentException(
                    "Product " + productId + " isn't existing in the wishlist.");
        }
        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
    public WishList moveToShoppingCart(Long userId, Long productId, int quantity) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if (wishList == null || wishList.getProducts()
                                        .isEmpty()) {
            throw new ResourceNotFoundException("Wishlist " + userId + " hasn't been found.");
        }
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product " + productId + " hasn't been found."));
        ShoppingCart shoppingCart = wishList.getUser()
                                            .getShoppingCart();
        Optional<Item> existingItem = shoppingCart.getItems()
                                                  .stream()
                                                  .filter(item -> item.getProduct()
                                                                      .getId()
                                                                      .equals(productId))
                                                  .findAny()
                ;
        if (existingItem.isPresent()) {
            if (product.getStockQuantity() + existingItem.get()
                                                         .getQuantity() < quantity) {
                throw new IllegalArgumentException(
                        "Insufficient stock: Requested " + quantity + ", but only " +
                        product.getStockQuantity() + " left in stock.");
            }
            product.setStockQuantity(product.getStockQuantity() + existingItem.get()
                                                                              .getQuantity() -
                                     quantity);
            existingItem.get()
                        .setQuantity(quantity);
        } else {
            if (product.getStockQuantity() - quantity < 0) {
                throw new IllegalArgumentException(
                        "Insufficient stock: Requested " + quantity + ", but only " +
                        product.getStockQuantity() + " left in stock.");
            }
            Item item = new Item();
            item.setQuantity(quantity);
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);
            shoppingCart.getItems()
                        .add(item);
            product.setStockQuantity(product.getStockQuantity() - quantity);
        }
        productRepository.save(product);
        double newTotalAmount = 0.0;
        for (Item item : shoppingCart.getItems()) {
            newTotalAmount += item.getProduct()
                                  .getPrice() * item.getQuantity();
        }
        shoppingCart.setTotalAmount(newTotalAmount);
        shoppingCartRepository.save(shoppingCart);
        wishList.getProducts()
                .remove(product);
        return wishList;
    }

    @Override
    @Transactional
    public WishList clearWishList(Long userId) {
        WishList wishList = wishListRepository.findWishListByUserId(userId);
        if (wishList == null || wishList.getProducts()
                                        .isEmpty()) {
            throw new IllegalArgumentException(
                    "Wish list for user " + userId + " is empty or not found.");
        }
        wishList.getProducts()
                .clear();
        return wishListRepository.save(wishList);
    }
}