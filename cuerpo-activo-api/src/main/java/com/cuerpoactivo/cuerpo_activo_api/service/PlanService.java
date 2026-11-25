package com.cuerpoactivo.cuerpo_activo_api.service;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listarPlanes() {
        return planRepository.findAll();
    }

    public Plan obtenerPlanPorId(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el plan con id " + id));
    }

    public Plan crearPlan(PlanRequest request) {
        Plan plan = request.aEntidad();
        return planRepository.save(plan);
    }

    public Plan actualizar(Long id, PlanRequest cambios) {
        Plan planExistente = obtenerPlanPorId(id);
        planExistente.setNombre(cambios.nombre());
        planExistente.setDuracionMeses(cambios.duracionMeses());
        planExistente.setPrecio(cambios.precio());
        return planRepository.save(planExistente);
    }

    public void eliminar(Long id) {
        obtenerPlanPorId(id);
        planRepository.deleteById(id);
    }
}