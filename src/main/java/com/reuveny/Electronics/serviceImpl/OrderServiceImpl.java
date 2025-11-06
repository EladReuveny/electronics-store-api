/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of OrderService to manage order-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.exception.ResourceNotFoundException;
import com.reuveny.Electronics.model.Item;
import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Product;
import com.reuveny.Electronics.model.Status;
import com.reuveny.Electronics.repository.OrderRepository;
import com.reuveny.Electronics.repository.ProductRepository;
import com.reuveny.Electronics.repository.ShoppingCartRepository;
import com.reuveny.Electronics.repository.UserRepository;
import com.reuveny.Electronics.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Status status) {
        return orderRepository.findById(orderId)
                              .map((existingOrder) -> {
                                  existingOrder.setStatus(status);
                                  return orderRepository.save(existingOrder);
                              })
                              .orElseThrow(() -> new ResourceNotFoundException(
                                      "Order " + orderId + " hasn't been found."));
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(() -> new ResourceNotFoundException(
                                             "Order " + orderId + " hasn't been found."));
        Duration duration = Duration.between(order.getOrderDate(), LocalDateTime.now());
        if (duration.toDays() <= 14) {
            for (Item item : order.getItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
            orderRepository.deleteById(orderId);
        } else {
            throw new IllegalArgumentException(
                    "Order can be canceled only within 14 days start from the order date.");
        }
    }
}