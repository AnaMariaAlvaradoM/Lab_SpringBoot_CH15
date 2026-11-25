package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
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

    public List<Plan> listar() {
        return planRepository.findAll();
    }

    public Plan crear(PlanRequest request) {
        Plan nuevoPlan = request.aEntidad();
        return planRepository.save(nuevoPlan);
    }

    public Plan buscarPorId(Long id) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        return encontrado.get();
    }

    public Plan actualizar(Long id, PlanRequest request) {
        Plan plan = buscarPorId(id);

        plan.setNombre(request.nombre());
        plan.setDuracionMeses(request.duracionMeses());
        plan.setPrecio(request.precio());

        return planRepository.save(plan);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        planRepository.deleteById(id);
    }
}
