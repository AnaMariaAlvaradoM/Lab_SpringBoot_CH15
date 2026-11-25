package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
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

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id).orElse(null);
    }

    public Plan guardar(PlanRequest request) {
        return planRepository.save(request.aEntidad());
    }

    public Optional<Plan> actualizar(Long id, PlanRequest request) {
        return planRepository.findById(id).map(planExistente -> {
            planExistente.setNombre(request.nombre());
            planExistente.setDuracionMeses(request.duracionMeses());
            planExistente.setPrecio(request.precio());
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
