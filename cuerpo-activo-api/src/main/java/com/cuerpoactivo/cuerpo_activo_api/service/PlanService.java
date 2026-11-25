package com.cuerpoactivo.cuerpo_activo_api.service;

import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    // Inyección de dependencias por constructor
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    // Listar todos los planes
    public List<Plan> listar() {
        return planRepository.buscarTodos();
    }

    // Buscar un plan por ID
    public Optional<Plan> buscarPorId(Long id) {
        return planRepository.buscarPorId(id);
    }

    // Crear un nuevo plan
    public Plan crear(Plan nuevo) {
        nuevo.setId(null);
        return planRepository.guardar(nuevo);
    }
}