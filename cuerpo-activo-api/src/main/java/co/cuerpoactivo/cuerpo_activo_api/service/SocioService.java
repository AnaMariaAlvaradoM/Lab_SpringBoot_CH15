package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
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

    public List<SocioResponse> buscarTodos() {
        return socioRepository.findAll()
                .stream()
                .map(this::aResponse)
                .toList();
    }

    public Optional<SocioResponse> buscarPorId(Long id) {
        return socioRepository.findById(id)
                .map(this::aResponse);
    }

    public Optional<SocioResponse> crear(SocioRequest request) {
        Optional<Plan> planOpt = planRepository.findById(request.planId());
        if (planOpt.isEmpty()) {
            return Optional.empty();
        }
        Socio socio = new Socio();
        socio.setNombre(request.nombre());
        socio.setPlan(planOpt.get());
        Socio guardado = socioRepository.save(socio);
        return Optional.of(aResponse(guardado));
    }

    public Optional<SocioResponse> actualizar(Long id, SocioRequest request) {
        Optional<Socio> socioOpt = socioRepository.findById(id);
        if (socioOpt.isEmpty()) {
            return Optional.empty();
        }
        Optional<Plan> planOpt = planRepository.findById(request.planId());
        if (planOpt.isEmpty()) {
            return Optional.empty();
        }
        Socio socio = socioOpt.get();
        socio.setNombre(request.nombre());
        socio.setPlan(planOpt.get());
        Socio actualizado = socioRepository.save(socio);
        return Optional.of(aResponse(actualizado));
    }

    public boolean eliminar(Long id) {
        if (!socioRepository.existsById(id)) {
            return false;
        }
        socioRepository.deleteById(id);
        return true;
    }

    private SocioResponse aResponse(Socio socio) {
        Plan plan = socio.getPlan();
        Long planId = plan != null ? plan.getId() : null;
        String planNombre = plan != null ? plan.getNombre() : null;
        return new SocioResponse(socio.getId(), socio.getNombre(), planId, planNombre);
    }
}