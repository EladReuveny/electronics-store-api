/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Status;
import com.reuveny.Electronics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable("userId") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestBody Status status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
