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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
        name = "Order Controller",
        description = "Handles all order-related endpoints"
)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Get orders for a specific user",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(
            @PathVariable("userId") Long userId
    ) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "Get all orders"
    )
    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "Get all orders in XML format"
    )
    @GetMapping(
            value = "/all/xml-format",
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> getAllOrdersAsXML() throws JsonProcessingException {
        List<Order> orders = orderService.getAllOrders();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String xmlString = xmlMapper.writeValueAsString(new OrderListWrapper(orders));
        return ResponseEntity.ok(xmlString);
    }

    @Operation(
            summary = "Update order status",
            parameters = {
                    @Parameter(
                            name = "orderId",
                            description = "The ID of the order to update",
                            required = true
                    ),
                    @Parameter(
                            name = "status",
                            description = "The new status to apply to the order",
                            required = true
                    )
            }
    )
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestBody Status status
    ) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels an order if within the allowed cancellation period."
    )
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order " + orderId + " has been canceled successfully.");
    }
}