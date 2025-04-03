package com.example.grandmasbliss_springboot.repository;


import com.example.grandmasbliss_springboot.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);
}
