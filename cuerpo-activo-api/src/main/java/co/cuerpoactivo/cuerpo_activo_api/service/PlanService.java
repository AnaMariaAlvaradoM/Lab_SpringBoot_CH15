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

    public Plan buscarPorId(Long id) {
        Optional<Plan> planOptional = planRepository.findById(id);
        if (planOptional.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        return planOptional.get();
    }

    public Plan crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        return planRepository.save(nuevo);
    }

    public Plan actualizar(Long id, PlanRequest datos) {
        Plan planExistente = buscarPorId(id);
        planExistente.setNombre(datos.nombre());
        planExistente.setDuracionMeses(datos.duracionMeses());
        planExistente.setPrecio(datos.precio());
        return planRepository.save(planExistente);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        planRepository.deleteById(id);
    }
}
