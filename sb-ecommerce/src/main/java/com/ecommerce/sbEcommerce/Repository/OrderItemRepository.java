package com.ecommerce.sbEcommerce.Repository;

import com.ecommerce.sbEcommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
