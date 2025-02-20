package com.phatbee.cosmeticshopbackend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phatbee.cosmeticshopbackend.Utils.JsonToMapConverter;
import jakarta.persistence.*;

import java.util.Map;

@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineId;


    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    @JsonManagedReference
    private Product product;


    @Convert(converter = JsonToMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Object> productSnapshot;  // Metadata stored as JSON map


    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    @JsonBackReference
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, Object> getProductSnapshot() {
        return productSnapshot;
    }

    public void setProductSnapshot(Map<String, Object> productSnapshot) {
        this.productSnapshot = productSnapshot;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
