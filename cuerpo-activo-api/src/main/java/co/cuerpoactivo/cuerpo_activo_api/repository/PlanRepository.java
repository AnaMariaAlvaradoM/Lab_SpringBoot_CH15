package co.cuerpoactivo.cuerpo_activo_api.repository;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {

}
