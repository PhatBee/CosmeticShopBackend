package com.phatbee.cosmeticshopbackend.dto;

import com.phatbee.cosmeticshopbackend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductSalesDTO {
    private Product product;
    private Long totalQuantity;

    public ProductSalesDTO(Product product, Long totalQuantity) {
        this.product = product;
        this.totalQuantity = totalQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
