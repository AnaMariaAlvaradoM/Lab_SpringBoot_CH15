package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    private final PlanRepository planRepository;
    private final SocioRepository socioRepository;

    public SocioService(PlanRepository planRepository, SocioRepository socioRepository) {
        this.planRepository = planRepository;
        this.socioRepository = socioRepository;
    }

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Optional<Socio> buscarPorId(Long id) {
        return socioRepository.findById(id);
    }

    public Optional<Socio> crear(SocioRequest request) {
        Optional<Plan> plan = planRepository.findById(request.planId());
        if (plan.isEmpty()) {
            return Optional.empty();
        }
        Socio nuevo = request.aEntidad(plan.get());
        return Optional.of(socioRepository.save(nuevo));
    }
}
