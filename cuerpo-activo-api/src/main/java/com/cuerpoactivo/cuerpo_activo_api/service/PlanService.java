package com.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;

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
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el plan con id " + id));
    }

    public Plan crear(PlanRequest datos) {
        Plan nuevo = datos.aEntidad();
        // El id lo asigna la base de datos, aunque el cliente envíe otro.
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Plan actualizar(Long id, PlanRequest cambios) {
        Plan plan = buscarPorId(id);
        plan.setNombre(cambios.nombre());
        plan.setDuracionMeses(cambios.duracionMeses());
        plan.setPrecio(cambios.precio());
        return planRepository.save(plan);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe el plan con id " + id);
        }
        planRepository.deleteById(id);
    }
}
