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

    public List<Plan> listar() {
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan crear(PlanRequest request) {
        Plan planGuardado = planRepository.save(request.aEntidad());
        return planRepository.save(planGuardado);
    }

    public Optional<Plan> actualizar(Long id, PlanRequest request) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            return Optional.empty();
        }
        Plan planes = encontrado.get();
        planes.setNombre(request.nombre());
        planes.setDuracionMeses(request.duracionMeses());
        planes.setPrecio(request.Precio());
        return Optional.of(planRepository.save(planes));
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }
        planRepository.deleteById(id);
        return true;
    }

}
