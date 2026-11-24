package co.cuerpoactivo.cuerpo_activo_api.service;

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

    public List<Plan> buscarTodos() {
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan guardar(Plan nuevo) {
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Optional<Plan> actualizar(Long id, Plan datos) {
        return planRepository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setDuracionMeses(datos.getDuracionMeses());
            existente.setPrecio(datos.getPrecio());
            return planRepository.save(existente);
        });
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }
        planRepository.deleteById(id);
        return true;
    }
}