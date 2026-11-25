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
        return repository.buscarTodos();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public Plan guardar(Plan plan) {

        plan.setId(null);

        return repository.guardar(plan);
    }
}
