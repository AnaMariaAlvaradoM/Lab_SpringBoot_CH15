package com.cuerpoactivo.cuerpo_activo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
