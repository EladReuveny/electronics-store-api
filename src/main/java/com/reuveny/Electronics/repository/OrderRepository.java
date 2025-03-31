/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Provides database access for order-related operations.
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
