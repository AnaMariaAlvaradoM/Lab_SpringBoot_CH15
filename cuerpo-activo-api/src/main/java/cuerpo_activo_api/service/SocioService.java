package cuerpo_activo_api.service;

import cuerpo_activo_api.dto.SocioRequest;
import cuerpo_activo_api.exception.RecursoNoEncontradoException;
import cuerpo_activo_api.exception.ReglaDeNegocioException;
import cuerpo_activo_api.model.Plan;
import cuerpo_activo_api.model.Socio;
import cuerpo_activo_api.repository.PlanRepository;
import cuerpo_activo_api.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Socio crear(SocioRequest request) {
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ReglaDeNegocioException("No existe el plan con id " + request.planId()));
        return socioRepository.save(request.aEntidad(plan));
    }
}