package co.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
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

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el plan con id " + id));
    }

    public Plan crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Plan actualizar(Long id, PlanRequest request) {
        Plan existente = buscarPorId(id);
        existente.setNombre(request.nombre());
        existente.setDuracionMeses(request.duracionMeses());
        existente.setPrecio(request.precio());
        return planRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        planRepository.deleteById(id);
    }
}