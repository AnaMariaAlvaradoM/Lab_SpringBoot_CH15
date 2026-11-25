package co.cuerpoactivo.cuerpo_activo.api.repository;

import co.cuerpoactivo.cuerpo_activo.api.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

}
