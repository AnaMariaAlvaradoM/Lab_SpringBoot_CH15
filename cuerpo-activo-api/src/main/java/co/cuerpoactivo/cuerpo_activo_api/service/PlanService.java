package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
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

    public Plan buscarPorId(Long id) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        return encontrado.get();
//        return planRepository.findById(id);
    }

    public Plan crear(PlanRequest request) {
//        request.setId(null);

        Plan nuevo = request.aEntidad();
        return planRepository.save(nuevo);
    }


    public Plan actualizar(Long id, PlanRequest datos) {
        Plan plan = buscarPorId(id);
//        Optional<Plan> encontrado = planRepository.findById(id);
//        if (encontrado.isEmpty()) {
//            return Optional.empty();
//        }
//        Plan plan = encontrado.get();
        plan.setNombre(datos.nombre());
        plan.setDuracionMeses(datos.duracionMeses());
        plan.setPrecio(datos.precio());
        return planRepository.save(plan);
    }


    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el producto con id " + id);
        }
        planRepository.deleteById(id);
//        return true;
    }

}
