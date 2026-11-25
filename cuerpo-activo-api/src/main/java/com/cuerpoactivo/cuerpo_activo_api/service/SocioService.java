package com.cuerpoactivo.cuerpo_activo_api.service;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.exception.ReglaDeNegocioException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioService {

    private final SocioRepository socioRepository;
    private final PlanRepository planRepository;

    public SocioService(SocioRepository socioRepository,
                        PlanRepository planRepository) {
        this.socioRepository = socioRepository;
        this.planRepository = planRepository;
    }

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el socio con id " + id));
    }

    public Socio crear(SocioRequest request) {
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ReglaDeNegocioException(
                        "No existe el plan con id " + request.planId()));

        return socioRepository.save(request.aEntidad(plan));
    }
}