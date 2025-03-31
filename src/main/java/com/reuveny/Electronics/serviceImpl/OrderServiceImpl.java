/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of OrderService to manage order-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.model.*;
import com.reuveny.Electronics.repository.OrderRepository;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.repository.UserRepository;
import com.reuveny.Electronics.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

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

    /**
     * Cancels an order if it is within 14 days of the order date.
     *
     * @param orderId The ID of the order to cancel.
     * @throws IllegalArgumentException If the order is not found or cannot be canceled.
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order " + orderId + " hasn't been found."));

        Duration duration = Duration.between(order.getOrderDate(), LocalDateTime.now());
        if(duration.toDays() <= 14) {
            for(Item item: order.getItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity()
                        + item.getQuantity());

                productRepository.save(product);
            }
            orderRepository.deleteById(orderId);
        } else {
            throw new IllegalArgumentException("Order can be canceled only within 14 days start from the order date.");
        }
    }
}