package com.cuerpoactivo.cuerpo_activo_api.repository;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,Long> {

}
