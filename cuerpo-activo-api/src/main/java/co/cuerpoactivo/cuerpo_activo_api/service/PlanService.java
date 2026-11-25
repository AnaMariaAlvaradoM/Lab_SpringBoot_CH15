package co.cuerpoactivo.cuerpo_activo_api.service;

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

    public List<Plan> buscarTodos() {
        return planRepository.findAll();
    }

    public Plan buscarPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plan con id " + id + " no existe"));
    }

    public Plan guardar(Plan nuevo) {
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Plan actualizar(Long id, Plan datos) {
        Plan existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDuracionMeses(datos.getDuracionMeses());
        existente.setPrecio(datos.getPrecio());
        return planRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("El plan con id " + id + " no existe");
        }
        planRepository.deleteById(id);
    }
}