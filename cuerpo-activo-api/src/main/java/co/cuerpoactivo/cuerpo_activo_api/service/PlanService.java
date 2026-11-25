
package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> buscarTodos() {
        return planRepository.buscarTodos();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.buscarPorId(id);
    }

    public Plan guardar(Plan plan) {
        plan.setId(null);
        return planRepository.guardar(plan);
    }
}
