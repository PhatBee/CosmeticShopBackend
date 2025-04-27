package com.phatbee.cosmeticshopbackend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @ManyToOne
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID")
    private Category category;
    private String productName;
    private String productCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate manufactureDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String brand;
    private String origin;
    @Column(columnDefinition = "text")
    private String ingredient;
    private String image;
    private double price;
    @Column(columnDefinition = "text")
    private String how_to_use;
    @Column(columnDefinition = "text")
    private String description;
    private String volume;
    private Boolean active;
}
