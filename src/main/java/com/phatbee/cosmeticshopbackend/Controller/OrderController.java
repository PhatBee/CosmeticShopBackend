package com.phatbee.cosmeticshopbackend.Controller;

import com.phatbee.cosmeticshopbackend.dto.OrderLineRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.OrderRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.PaymentRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.ShippingAddressRequestDTO;
import com.phatbee.cosmeticshopbackend.Entity.Order;
import com.phatbee.cosmeticshopbackend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders/create")
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order-lines/create")
    public ResponseEntity<Void> createOrderLines(@RequestBody List<OrderLineRequestDTO> orderLineRequests) {
        // In a real app, you'd pass the orderId from the previous response.
        // For simplicity, assume the last order is the one we just created.
        Order lastOrder = orderService.getLastOrder();
        int orderId = lastOrder.getOrderId();
        orderService.createOrderLines(orderLineRequests, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payments/create")
    public ResponseEntity<Void> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        Order lastOrder = orderService.getLastOrder();
        int orderId = lastOrder.getOrderId();
        orderService.createPayment(paymentRequestDTO, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/shipping-addresses/create")
    public ResponseEntity<Void> createShippingAddress(@RequestBody ShippingAddressRequestDTO shippingAddressRequestDTO) {
        Order lastOrder = orderService.getLastOrder();
        int orderId = lastOrder.getOrderId();
        orderService.createShippingAddress(shippingAddressRequestDTO, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        orderService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}