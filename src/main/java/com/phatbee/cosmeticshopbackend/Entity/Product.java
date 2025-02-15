package com.phatbee.cosmeticshopbackend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private boolean active;
    private int productID;
    private int categoryID;
    private String productName;
    private String productCode;
    private Date manufactureDate;
    private Date expireDate;
    private String brand;
    private String origin;
    private String ingredient;
    private String image;
    private double price;
    private String how_to_use;
    private String description;
    private int volume;
}
