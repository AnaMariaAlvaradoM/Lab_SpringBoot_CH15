package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listar() {
        return planRepository.findAll(Sort.by("id"));
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el plan con id " + id));
    }

    public Plan crear(PlanRequest request) {
        return planRepository.save(request.aEntidad());
    }

    @Transactional
    public Plan actualizar(Long id, PlanRequest cambios) {
        Plan plan = buscarPorId(id);
        plan.setNombre(cambios.nombre());
        plan.setDuracionMeses(cambios.duracionMeses());
        plan.setPrecio(cambios.precio());
        return planRepository.save(plan);
    }

    @Transactional
    public void eliminar(Long id) {
        planRepository.delete(buscarPorId(id));
    }
}
