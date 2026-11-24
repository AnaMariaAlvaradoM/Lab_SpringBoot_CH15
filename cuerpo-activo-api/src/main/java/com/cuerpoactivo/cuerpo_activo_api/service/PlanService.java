package com.cuerpoactivo.cuerpo_activo_api.service;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listarPlanes() {
        return planRepository.findAll();
    }

    public Optional<Plan> obtenerPlanPorId(Long id) {
        return planRepository.findById(id);
    }

    public Plan crearPlan(Plan plan) {
        plan.setId(null);
        return planRepository.save(plan);
    }
}