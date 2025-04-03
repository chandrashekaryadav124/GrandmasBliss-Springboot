package com.example.grandmasbliss_springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.grandmasbliss_springboot.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
