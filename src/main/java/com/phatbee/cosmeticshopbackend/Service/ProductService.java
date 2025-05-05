package com.phatbee.cosmeticshopbackend.Service;

import com.phatbee.cosmeticshopbackend.Entity.Product;
import com.phatbee.cosmeticshopbackend.dto.ProductSalesDTO;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(int categoryID);
    List<Product> getRecentProducts();
    List<ProductSalesDTO> getTopSellingProducts();
    Product findById(int productId);
}
