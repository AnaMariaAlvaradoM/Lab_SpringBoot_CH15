package co.cuerpoactivo.cuerpo_activo.api.repository;

import co.cuerpoactivo.cuerpo_activo.api.model.Plan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryEnMemoria implements PlanRepository{

    private final List<Plan> planes = new ArrayList<>();
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
        for (Plan plan : planes) {
            if (plan.getId().equals(id)) {
                return Optional.of(plan);
            }
        }
        return Optional.empty();
    }

    @Override
    public Plan guardar(Plan plan) {
        if (plan.getId() == null) {
            plan.setId(siguienteId);
            siguienteId++;
            planes.add(plan);
        }
        return plan;
    }
}
