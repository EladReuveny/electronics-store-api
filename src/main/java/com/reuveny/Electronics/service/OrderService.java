/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Handles order-related operations, including order creation, cancellation, and status updates.
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
     * ( Admin only)
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status to set for the order.
     * @return The updated order.
     * @throws IllegalArgumentException If the order with the specified ID is not found.
     */
    Order updateOrderStatus(Long orderId, Status status);

    /**
     * Cancels an order if it is within 14 days of the order date.
     *
     * @param orderId The ID of the order to cancel.
     * @throws IllegalArgumentException If the order is not found or cannot be canceled.
     */
    void cancelOrder(Long orderId);
}
