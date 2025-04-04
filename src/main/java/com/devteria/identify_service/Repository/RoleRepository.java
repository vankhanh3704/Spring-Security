package com.devteria.identify_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteria.identify_service.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
