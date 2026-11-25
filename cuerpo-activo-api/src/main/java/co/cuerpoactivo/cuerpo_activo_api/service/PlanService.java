package co.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;

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

    public Plan guardar(Plan nuevo) {
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Optional<Plan> actualizar(Long id, Plan cambios) {
        Optional<Plan> existente = planRepository.findById(id);
        if (existente.isEmpty()) {
            return Optional.empty();
        }
        Plan plan = existente.get();
        plan.setNombre(cambios.getNombre());
        plan.setDuracionMeses(cambios.getDuracionMeses());
        plan.setPrecio(cambios.getPrecio());
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