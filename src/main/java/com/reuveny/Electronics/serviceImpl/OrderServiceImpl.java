/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service implementation for managing orders in the system.
 * This includes fetching orders by user, retrieving all orders, and updating the order status.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.model.*;
import com.reuveny.Electronics.repository.OrderRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.repository.UserRepository;
import com.reuveny.Electronics.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    /**
     * Retrieves all orders placed by a user, identified by their user ID.
     *
     * @param userId The ID of the user whose orders are to be fetched.
     * @return A list of orders made by the specified user.
     */
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return A list of all orders across all users.
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Updates the status of an order.
     * This operation is typically available to an admin to update the order's status (e.g., "Shipped", "Delivered").
     *
     * @param orderId The ID of the order to be updated.
     * @param status  The new status to be applied to the order.
     * @return The updated order object after the status change.
     * @throws IllegalArgumentException If the order with the specified ID is not found.
     */
    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Status status) {
        return orderRepository.findById(orderId)
                .map((existingOrder) -> {
                    existingOrder.setStatus(status);
                    return orderRepository.save(existingOrder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Order " + orderId + " hasn't been found."));
    }
}