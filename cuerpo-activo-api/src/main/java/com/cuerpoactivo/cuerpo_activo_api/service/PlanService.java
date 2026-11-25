package com.cuerpoactivo.cuerpo_activo_api.service;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
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

    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan crear(PlanRequest request) {
        return planRepository.save(request.aEntidad());
    }

    public Optional<Plan> actualizar(Long id, PlanRequest request) {
        return planRepository.findById(id).map(plan -> {
            plan.setNombre(request.nombre());
            plan.setDuracionMeses(request.duracionMeses());
            plan.setPrecio(request.precio());

            return planRepository.save(plan);
        });
    }

    public boolean eliminar(Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return true;
        }

        return false;
    }
}