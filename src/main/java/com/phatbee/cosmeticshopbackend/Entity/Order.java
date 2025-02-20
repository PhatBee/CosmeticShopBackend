package com.phatbee.cosmeticshopbackend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phatbee.cosmeticshopbackend.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    private int customerID;
    private LocalDateTime orderDate;
    private Double total;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime deliveryDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderLine> orderLines;


}
