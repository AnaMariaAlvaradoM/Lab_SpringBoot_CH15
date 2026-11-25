package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
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
        if (request.planId() == null) {
            return Optional.empty();
        }
        return planRepository.findById(request.planId()).map(plan -> {
            Socio nuevo = request.aEntidad(plan);
            nuevo.setId(null);
            return socioRepository.save(nuevo);
        });
    }
}