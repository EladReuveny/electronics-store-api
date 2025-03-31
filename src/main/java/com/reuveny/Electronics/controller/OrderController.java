/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for handling orders, including fetching, updating, and XML formatting.
 */
package com.reuveny.Electronics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reuveny.Electronics.model.Order;
import com.reuveny.Electronics.model.Status;
import com.reuveny.Electronics.service.OrderService;
import com.reuveny.Electronics.xml.OrderListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return ResponseEntity containing the list of orders or 404 if none found.
     */
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable("userId") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    /**
     * Retrieves all orders in the system.
     *
     * @return ResponseEntity containing the list of orders.
     */
    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Retrieves all orders in XML format.
     *
     * @return ResponseEntity containing the XML representation of all orders.
     * @throws JsonProcessingException If an error occurs during XML conversion.
     */
    @GetMapping(value = "/all/xml-format", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllOrdersAsXML() throws JsonProcessingException {
        List<Order> orders = orderService.getAllOrders();

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String xmlString = xmlMapper.writeValueAsString(new OrderListWrapper(orders));

        return ResponseEntity.ok(xmlString);
    }

    /**
     * Updates the status of an order.
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status of the order.
     * @return ResponseEntity containing the updated order or a 404 status if not found.
     */
    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestBody Status status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    /**
     * Cancels an order if it is within the allowed cancellation period (e.g., 14 days).
     *
     * @param orderId The ID of the order to cancel.
     * @return A response entity with a success message or an error message if the cancellation is not allowed.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order " + orderId + " has been canceled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}