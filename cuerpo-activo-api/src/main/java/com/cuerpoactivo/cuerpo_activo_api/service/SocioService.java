package com.cuerpoactivo.cuerpo_activo_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.exception.ReglaDeNegocioException;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import com.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;

@Service
public class SocioService {

    private final SocioRepository socioRepository;
    private final PlanRepository planRepository;

    public SocioService(SocioRepository socioRepository, PlanRepository planRepository) {
        this.socioRepository = socioRepository;
        this.planRepository = planRepository;
    }

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el socio con id " + id));
    }

    public Socio crear(SocioRequest datos) {
        Plan plan = planRepository.findById(datos.planId())
                .orElseThrow(() -> new ReglaDeNegocioException("No existe el plan con id " + datos.planId()));
        return socioRepository.save(datos.aEntidad(plan));
    }
}
