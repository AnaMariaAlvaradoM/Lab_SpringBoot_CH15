package co.cuerpoactivo.cuerpo_activo_api.repository;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    List<Plan> buscarTodos();
    Optional<Plan> buscarPorId(Long id);
    Plan guardar(Plan plan);
}
