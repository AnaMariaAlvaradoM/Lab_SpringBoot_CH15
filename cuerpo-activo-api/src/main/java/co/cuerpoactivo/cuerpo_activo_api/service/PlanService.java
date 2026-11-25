package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
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

    public List<PlanResponse> buscarTodos() {
        return planRepository.findAll().stream()
                .map(PlanResponse::desde)
                .toList();
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el plan con id " + id));
    }

    public PlanResponse crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        nuevo.setId(null);
        Plan guardado = planRepository.save(nuevo);
        return PlanResponse.desde(guardado);
    }

    public Plan actualizar(Long id, PlanRequest cambios) {
        Plan existente = buscarPorId(id);
        existente.setNombre(cambios.nombre());
        existente.setDuracionMeses(cambios.duracionMeses());
        existente.setPrecio(cambios.precio());
        return planRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        planRepository.deleteById(id);
    }
}