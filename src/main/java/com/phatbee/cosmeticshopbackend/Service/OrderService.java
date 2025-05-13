package com.phatbee.cosmeticshopbackend.Service;

import com.phatbee.cosmeticshopbackend.Entity.Order;
import com.phatbee.cosmeticshopbackend.dto.*;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequestDTO);
    void createOrderLines(List<OrderLineRequestDTO> orderLineRequests, int orderId);
    void createPayment(PaymentRequestDTO paymentRequestDTO, int orderId);
    void createShippingAddress(ShippingAddressRequestDTO shippingAddressRequestDTO, int orderId);
    void clearCart(Long userId);
    Order getLastOrder();

    Map<String, List<Order>> getOrdersByUserId(Long userId);

}
