package com.phatbee.cosmeticshopbackend.Repository;

import com.phatbee.cosmeticshopbackend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByActiveTrue();
}
