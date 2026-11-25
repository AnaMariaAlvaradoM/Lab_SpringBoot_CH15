
package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // Consultar todos los planes
    public List<Plan> buscarTodos() {
        return planRepository.findAll();
    }

    // Buscar un plan por ID
    public Plan buscarPorId(Long id) {

        return planRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el plan con id " + id
                        )
                );
    }

    // Crear un nuevo plan
    public Plan crear(PlanRequest request) {

        Plan nuevo = request.aEntidad();

        return planRepository.save(nuevo);
    }

    // Actualizar un plan
    public Plan actualizar(Long id, PlanRequest cambios) {

        Plan plan = buscarPorId(id);

        plan.setNombre(cambios.nombre());
        plan.setDuracionMeses(cambios.duracionMeses());
        plan.setPrecio(cambios.precio());

        return planRepository.save(plan);
    }

    // Eliminar un plan
    public void eliminar(Long id) {

        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException(
                    "No existe el plan con id " + id
            );
        }

        planRepository.deleteById(id);
    }
}



