package com.payflow.payflow.repository.product;

import com.payflow.payflow.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository{
}
