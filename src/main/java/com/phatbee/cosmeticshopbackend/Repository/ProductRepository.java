package com.phatbee.cosmeticshopbackend.Repository;

import com.phatbee.cosmeticshopbackend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByActiveTrue();
    List<Product> findByCategory_CategoryID(int categoryID);

    @Query("SELECT p FROM Product p WHERE p.createdDate >= :startDate ORDER BY p.createdDate desc ")
    List<Product> findRecentProducts (@Param("startDate") LocalDate startDate);
    @Query("SELECT ol.product, SUM(ol.quantity) AS totalQuantity FROM OrderLine ol " +
            "GROUP BY ol.product " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProducts();
}
