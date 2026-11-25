
package co.cuerpoactivo.cuerpo_activo_api.repository;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryEnMemoria implements PlanRepository {

    private List<Plan> planes = new ArrayList<>();
    private Long siguienteId = 4L;

    public PlanRepositoryEnMemoria() {
        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
    }

    @Override
    public List<Plan> buscarTodos() {
        return planes;
    }

    @Override
    public Optional<Plan> buscarPorId(Long id) {
        return planes.stream()
                .filter(plan -> plan.getId().equals(id))
                .findFirst();
    }

    @Override
    public Plan guardar(Plan plan) {
        plan.setId(siguienteId++);
        planes.add(plan);
        return plan;
    }
}
