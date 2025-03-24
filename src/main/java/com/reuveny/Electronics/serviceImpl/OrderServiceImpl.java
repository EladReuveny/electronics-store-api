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
                .orElseThrow(() -> new IllegalArgumentException("Order " + orderId + " hasn't been found."));
    }
}
