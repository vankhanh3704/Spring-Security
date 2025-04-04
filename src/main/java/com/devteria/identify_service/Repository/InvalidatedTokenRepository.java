package com.devteria.identify_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devteria.identify_service.Entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
