package com.ecommerce.sbEcommerce.Repository;

import com.ecommerce.sbEcommerce.Enum.AppRole;
import com.ecommerce.sbEcommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {

    Optional<Role> findByRoleName(AppRole appRole);
}
