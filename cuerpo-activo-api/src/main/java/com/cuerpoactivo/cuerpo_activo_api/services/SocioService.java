package com.cuerpoactivo.cuerpo_activo_api.services;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import com.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Socio> buscarPorId(Long id) {
        return socioRepository.findById(id);
    }

    public Optional<Socio> crear(SocioRequest datos) {
        Optional<Plan> planEncontrado = planRepository.findById(datos.planId());
        if (planEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Socio nuevoSocio = datos.aEntidad(planEncontrado.get());
        nuevoSocio.setId(null);
        Socio guardado = socioRepository.save(nuevoSocio);
        return Optional.of(guardado);
    }
}
