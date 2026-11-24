package com.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public Plan guardar(Plan nuevo) {
        // El id lo asigna la base de datos, aunque el cliente envíe otro.
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }
}
