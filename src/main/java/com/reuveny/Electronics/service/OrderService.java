/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Handles order-related operations, including order creation, cancellation, and status updates.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.exception.ResourceNotFoundException;
import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Status;

import java.util.List;

public interface OrderService {
    /**
     * Retrieves all orders placed by a user, identified by their user ID.
     *
     * @param userId The ID of the user whose orders are to be fetched.
     * @return A list of orders made by the specified user.
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Retrieves all orders in the system.
     *
     * @return A list of all orders across all users.
     */
    List<Order> getAllOrders();

    /**
     * Updates the status of an order.
     * This operation is typically available to an admin to update the order's status (e.g., "Shipped", "Delivered").
     *
     * @param orderId The ID of the order to be updated.
     * @param status  The new status to be applied to the order.
     * @return The updated order object after the status change.
     * @throws ResourceNotFoundException If the order with the specified ID is not found.
     */
    Order updateOrderStatus(Long orderId, Status status);

    /**
     * Cancels an order if it is within 14 days of the order date.
     *
     * @param orderId The ID of the order to cancel.
     * @throws IllegalArgumentException  If the order cannot be canceled.
     * @throws ResourceNotFoundException If the order with the specified ID is not found.
     */
    void cancelOrder(Long orderId);
}
