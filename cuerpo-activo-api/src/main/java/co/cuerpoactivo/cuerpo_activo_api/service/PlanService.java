package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
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

    public Optional<Plan> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Plan guardar(Plan plan) {
        plan.setId(null);
        return repository.save(plan);
    }

    public Optional<Plan> actualizar(Long id, Plan datosNuevos) {
        return repository.findById(id)
                .map(planExistente -> {
                    planExistente.setNombre(datosNuevos.getNombre());
                    planExistente.setDuracionMeses(datosNuevos.getDuracionMeses());
                    planExistente.setPrecio(datosNuevos.getPrecio());

                    return repository.save(planExistente);
                });

    }
    public void borrar(Long id){
        repository.deleteById(id);
    }
}
