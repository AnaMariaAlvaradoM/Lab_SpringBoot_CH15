package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SocioRepository socioRepository;

    public PlanService(PlanRepository planRepository, SocioRepository socioRepository) {
        this.planRepository = planRepository;
        this.socioRepository = socioRepository;
    }

    public List<Plan> listar() {
        return planRepository.findAll();
    }

    public Plan buscarPorId(Long id) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        return encontrado.get();
    }

    public Plan crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        return planRepository.save(nuevo);
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