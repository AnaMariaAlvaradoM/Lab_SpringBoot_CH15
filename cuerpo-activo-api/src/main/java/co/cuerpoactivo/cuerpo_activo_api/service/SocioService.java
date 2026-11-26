package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import co.cuerpoactivo.cuerpo_activo_api.exception.ReglaDeNegocioException;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
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

    public Socio buscarPorId(Long id) {
        Optional<Socio> socioOptional = socioRepository.findById(id);
        if (socioOptional.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe el socio con id " + id);
        }
        return socioOptional.get();
    }

    public Socio crear(SocioRequest request) {
        Optional<Plan> planOptional = planRepository.findById(request.planId());
        if (planOptional.isEmpty()) {
            throw new ReglaDeNegocioException("No existe el plan con el id " + request.planId());
        }
        Socio nuevo = request.aEntidad(planOptional.get());
        return socioRepository.save(nuevo);
    }
}
