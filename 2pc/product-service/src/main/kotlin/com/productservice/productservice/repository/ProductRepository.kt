package com.productservice.productservice.repository

import com.productservice.productservice.entity.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository: CrudRepository<Product, Long> {
}