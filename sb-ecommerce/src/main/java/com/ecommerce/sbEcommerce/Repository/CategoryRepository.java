package com.ecommerce.sbEcommerce.Repository;

import com.ecommerce.sbEcommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}
