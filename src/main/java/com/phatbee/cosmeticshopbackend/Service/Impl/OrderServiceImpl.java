package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Entity.*;
import com.phatbee.cosmeticshopbackend.Repository.*;
import com.phatbee.cosmeticshopbackend.Service.OrderService;
import com.phatbee.cosmeticshopbackend.dto.OrderLineRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.OrderRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.PaymentRequestDTO;
import com.phatbee.cosmeticshopbackend.dto.ShippingAddressRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        // Validate user
        Optional<User> userOptional = userRepository.findById(orderRequestDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + orderRequestDTO.getUserId());
        }
        User user = userOptional.get();

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotal(orderRequestDTO.getTotal());
        order.setOrderStatus(orderRequestDTO.getOrderStatus());
        order.setDeliveryDate(orderRequestDTO.getDeliveryDate());
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void createOrderLines(List<OrderLineRequestDTO> orderLineRequests, int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        Order order = orderOptional.get();

        for (OrderLineRequestDTO request : orderLineRequests) {
            Optional<Product> productOptional = productRepository.findById(request.getProductId());
            if (productOptional.isEmpty()) {
                throw new RuntimeException("Product not found with ID: " + request.getProductId());
            }
            Product product = productOptional.get();

            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setProduct(product);
            orderLine.setQuantity(request.getQuantity());
            orderLine.setProductSnapshot(request.getProductSnapshot());
            orderLineRepository.save(orderLine);
        }
    }

    @Override
    @Transactional
    public void createPayment(PaymentRequestDTO paymentRequestDTO, int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        Order order = orderOptional.get();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentRequestDTO.getPaymentMethod());
        payment.setPaymentStatus(paymentRequestDTO.getPaymentStatus());
        payment.setTotal(paymentRequestDTO.getTotal());
        payment.setPaymentDate(paymentRequestDTO.getPaymentDate());
        paymentRepository.save(payment);

        order.setPayment(payment);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void createShippingAddress(ShippingAddressRequestDTO shippingAddressRequestDTO, int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        Order order = orderOptional.get();

        ShippingAddress shippingAddress = getShippingAddress(shippingAddressRequestDTO, order);
        shippingAddressRepository.save(shippingAddress);

        order.setShippingAddress(shippingAddress);
        orderRepository.save(order);
    }

    private static ShippingAddress getShippingAddress(ShippingAddressRequestDTO shippingAddressRequestDTO, Order order) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setOrder(order);
        shippingAddress.setReceiverName(shippingAddressRequestDTO.getReceiverName());
        shippingAddress.setReceiverPhone(shippingAddressRequestDTO.getReceiverPhone());
        shippingAddress.setAddress(shippingAddressRequestDTO.getAddress());
        shippingAddress.setProvince(shippingAddressRequestDTO.getProvince());
        shippingAddress.setDistrict(shippingAddressRequestDTO.getDistrict());
        shippingAddress.setWard(shippingAddressRequestDTO.getWard());
        return shippingAddress;
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByCustomer_UserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    @Override
    public Order getLastOrder() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) return null;
        return orders.get(orders.size() - 1);
    }
}
