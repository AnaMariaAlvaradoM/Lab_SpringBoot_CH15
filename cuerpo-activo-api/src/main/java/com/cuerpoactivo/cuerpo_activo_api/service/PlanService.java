package com.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
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

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan crear(PlanRequest datos) {
        Plan nuevo = datos.aEntidad();
        // El id lo asigna la base de datos, aunque el cliente envíe otro.
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }

    public Optional<Plan> actualizar(Long id, PlanRequest cambios) {
        return planRepository.findById(id).map(plan -> {
            plan.setNombre(cambios.nombre());
            plan.setDuracionMeses(cambios.duracionMeses());
            plan.setPrecio(cambios.precio());
            return planRepository.save(plan);
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
