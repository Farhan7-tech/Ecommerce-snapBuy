package com.ecommerce.sbEcommerce.Repository;

import com.ecommerce.sbEcommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
