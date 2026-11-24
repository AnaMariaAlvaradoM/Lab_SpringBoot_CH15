package cuerpo_activo_api.service;

import cuerpo_activo_api.dto.SocioRequest;
import cuerpo_activo_api.model.Socio;
import cuerpo_activo_api.repository.PlanRepository;
import cuerpo_activo_api.repository.SocioRepository;
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

    public Optional<Socio> crear(SocioRequest request) {
        return planRepository.findById(request.planId())
                .map(plan -> socioRepository.save(request.aEntidad(plan)));
    }
}