/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Provides database access for wishlist management.
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findWishListByUserId(Long userId);
}
