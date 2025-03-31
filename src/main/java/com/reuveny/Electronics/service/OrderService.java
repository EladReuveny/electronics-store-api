/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service interface for managing orders in the system, including retrieving orders by user and updating order statuses.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Status;

import java.util.List;

public interface OrderService {
    /**
     * Retrieves a list of orders for a specific user by their user ID.
     *
     * @param userId The ID of the user whose orders are to be fetched.
     * @return List of orders associated with the given user.
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Retrieves a list of all orders in the system.
     *
     * @return List of all orders.
     */
    List<Order> getAllOrders();

    /**
     * Updates the status of an existing order.
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status to set for the order.
     * @return The updated order.
     */
    Order updateOrderStatus(Long orderId, Status status); // Admin
}
