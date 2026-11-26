
package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
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

    public List<Plan> buscarTodos() {
        return planRepository.findAll();
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el plan con id " + id
                        )
                );
    }

    public Plan guardar(PlanRequest solicitud) {
        Plan plan = solicitud.aEntidad();
        return planRepository.save(plan);
    }

    public Plan actualizar(Long id, PlanRequest cambios) {
        Plan plan = buscarPorId(id);

        plan.setNombre(cambios.nombre());
        plan.setDuracionMeses(cambios.duracionMeses());
        plan.setPrecio(cambios.precio());

        return planRepository.save(plan);
    }

    public void eliminar(Long id) {
        Plan plan = buscarPorId(id);
        planRepository.delete(plan);
    }
}
