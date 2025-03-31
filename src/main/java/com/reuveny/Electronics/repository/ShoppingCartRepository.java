/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Manages database interactions for shopping cart data.
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findCartByUserId(Long userId);
}
