/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Status;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getAllOrders();
    Order updateOrderStatus(Long orderId, Status status); // Admin
}
