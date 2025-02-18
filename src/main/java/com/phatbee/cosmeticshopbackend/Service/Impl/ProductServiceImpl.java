package com.phatbee.cosmeticshopbackend.Service.Impl;

import com.phatbee.cosmeticshopbackend.Entity.Product;
import com.phatbee.cosmeticshopbackend.Repository.ProductRepository;
import com.phatbee.cosmeticshopbackend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    @Override
    public List<Product> getProductsByCategory(int categoryID) {
        return productRepository.findByCategory_CategoryID(categoryID);
    }

    @Override
    public List<Product> getRecentProducts() {
        LocalDate today = LocalDate.now();
        today = today.minusDays(7);
        return productRepository.findRecentProducts(today)
                .stream()
                .limit(10)
                .toList();
    }
}
