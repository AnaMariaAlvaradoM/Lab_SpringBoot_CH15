package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository repository;

    public PlanService(PlanRepository repository) {
        this.repository = repository;
    }

    public List<Plan> buscarTodos() {
        return repository.findAll();
    }

    public Plan buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el plan con id " + id));
    }

    public Plan guardar(PlanRequest request) {
        Plan plan = request.aEntidad();
        plan.setId(null);
        return repository.save(plan);
    }

    public Plan actualizar(Long id, PlanRequest request) {

        Plan planExistente = buscarPorId(id);

        planExistente.setNombre(request.nombre());
        planExistente.setDuracionMeses(request.duracionMeses());
        planExistente.setPrecio(request.precio());

        return repository.save(planExistente);
    }

    public void borrar(Long id){

        if (!repository.existsById(id)) {

            throw new RecursoNoEncontradoException(
                    "No existe el plan con id " + id);
        }

        repository.deleteById(id);
    }

}
