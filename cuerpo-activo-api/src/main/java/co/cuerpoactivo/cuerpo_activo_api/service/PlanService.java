
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
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan guardar(Plan plan) {
        plan.setId(null);
        return planRepository.save(plan);
    }

    public Optional<Plan> actualizar(Long id, Plan cambios) {
        return planRepository.findById(id).map(plan -> {
            plan.setNombre(cambios.getNombre());
            plan.setDuracionMeses(cambios.getDuracionMeses());
            plan.setPrecio(cambios.getPrecio());

            return planRepository.save(plan);
        });
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }

        planRepository.deleteById(id);
        return true;
    }
}
