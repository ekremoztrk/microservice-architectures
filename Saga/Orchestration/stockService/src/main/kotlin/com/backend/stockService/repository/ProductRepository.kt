package com.backend.stockService.repository

import com.backend.stockService.entity.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository: CrudRepository<Product, Long> {
}