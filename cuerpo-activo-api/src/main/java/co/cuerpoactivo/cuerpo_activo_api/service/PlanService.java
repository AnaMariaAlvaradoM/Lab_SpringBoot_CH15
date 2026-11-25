package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
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

    public List<PlanResponse> buscarTodos() {
        return planRepository.findAll().stream()
                .map(PlanResponse::desde)
                .toList();
    }

    public Optional<PlanResponse> buscarPorId(Long id) {
        return planRepository.findById(id)
                .map(PlanResponse::desde);
    }

    public PlanResponse crear(PlanRequest request) {
        Plan plan = request.aEntidad();
        plan.setId(null);
        Plan guardado = planRepository.save(plan);
        return PlanResponse.desde(guardado);
    }

    public Optional<PlanResponse> actualizar(Long id, PlanRequest cambios) {
        return planRepository.findById(id).map(existente -> {
            existente.setNombre(cambios.nombre());
            existente.setDuracionMeses(cambios.duracionMeses());
            existente.setPrecio(cambios.precio());
            Plan actualizado = planRepository.save(existente);
            return PlanResponse.desde(actualizado);
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