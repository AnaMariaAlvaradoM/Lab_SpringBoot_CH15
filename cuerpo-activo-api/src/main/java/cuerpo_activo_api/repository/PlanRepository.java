package cuerpo_activo_api.repository;

import cuerpo_activo_api.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}