/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Handles database interactions for product storage and retrieval.
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.Category;
import com.reuveny.Electronics.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findProductsByCategory(Category category);

    @Query(value = "DELETE FROM wishlists_products wp where product_id IN (:productIds)"
            , nativeQuery = true)
    @Modifying
    void removeProductReferences(@Param("productIds") List<Long> productIds);
}
