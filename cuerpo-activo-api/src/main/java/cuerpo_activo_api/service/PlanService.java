package cuerpo_activo_api.service;

import cuerpo_activo_api.dto.PlanRequest;
import cuerpo_activo_api.model.Plan;
import cuerpo_activo_api.repository.PlanRepository;
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
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan crear(PlanRequest request) {
        return planRepository.save(request.aEntidad());
    }

    public Optional<Plan> actualizar(Long id, PlanRequest cambios) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            return Optional.empty();
        }
        Plan plan = encontrado.get();
        plan.setNombre(cambios.nombre());
        plan.setDuracionMeses(cambios.duracionMeses());
        plan.setPrecio(cambios.precio());
        return Optional.of(planRepository.save(plan));
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }
        planRepository.deleteById(id);
        return true;
    }
}