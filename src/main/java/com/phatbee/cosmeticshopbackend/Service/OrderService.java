package com.phatbee.cosmeticshopbackend.Service;

import com.phatbee.cosmeticshopbackend.Entity.Order;
import com.phatbee.cosmeticshopbackend.dto.OrderLineRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.OrderRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.PaymentRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.ShippingAddressRequestDTO;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequestDTO);
    void createOrderLines(List<OrderLineRequestDTO> orderLineRequests, int orderId);
    void createPayment(PaymentRequestDTO paymentRequestDTO, int orderId);
    void createShippingAddress(ShippingAddressRequestDTO shippingAddressRequestDTO, int orderId);
    void clearCart(Long userId);
    Order getLastOrder();
}
