package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> buscarTodos() {
        return planRepository.findAll();
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id).orElse(null);
    }

    public Plan guardar(Plan nuevo) {
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }
    public java.util.Optional<Plan> actualizar(Long id, Plan cambios) {
        return planRepository.findById(id).map(planExistente -> {
            planExistente.setNombre(cambios.getNombre());
            planExistente.setDuracionMeses(cambios.getDuracionMeses());
            planExistente.setPrecio(cambios.getPrecio());
            return planRepository.save(planExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
