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

    public List<Plan> listar() {
        return planRepository.findAll();
    }

    public Plan crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        return planRepository.save(nuevo);
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el plan con id " + id
                ));
    }

    public Plan actualizar(Long id, PlanRequest request) {
        Plan encontrado = buscarPorId(id);

        encontrado.setNombre(request.nombre());
        encontrado.setDuracionMeses(request.duracionMeses());
        encontrado.setPrecio(request.precio());

        return planRepository.save(encontrado);
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }

        planRepository.deleteById(id);
        return true;
    }
}