package com.payflow.payflow.repository;

import com.payflow.payflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository{
}
