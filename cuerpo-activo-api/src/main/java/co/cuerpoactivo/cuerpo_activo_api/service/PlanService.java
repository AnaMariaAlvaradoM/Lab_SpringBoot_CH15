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
    public final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listar(){
        return planRepository.findAll();
    }

    public Plan buscarPorId(Long id){
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        return encontrado.get();
    }

    public Plan crear(PlanRequest request) {
        Plan nuevo = request.aEntidad();
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Plan actualizar(Long id, PlanRequest cambios){
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
