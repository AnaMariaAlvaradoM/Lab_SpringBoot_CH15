package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanResponse> buscarTodos() {
        return planRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    public Optional<PlanResponse> buscarPorId(Long id) {
        return planRepository.findById(id)
                .map(this::aResponse);
    }

    public PlanResponse guardar(PlanRequest request) {
        Plan plan = new Plan();
        plan.setNombre(request.nombre());
        plan.setDuracionMeses(request.duracionMeses());
        plan.setPrecio(request.precio());
        Plan guardado = planRepository.save(plan);
        return aResponse(guardado);
    }

    public Optional<PlanResponse> actualizar(Long id, PlanRequest request) {
        Optional<Plan> existente = planRepository.findById(id);
        if (existente.isEmpty()) {
            return Optional.empty();
        }
        Plan plan = existente.get();
        plan.setNombre(request.nombre());
        plan.setDuracionMeses(request.duracionMeses());
        plan.setPrecio(request.precio());
        Plan actualizado = planRepository.save(plan);
        return Optional.of(aResponse(actualizado));
    }

    public boolean eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            return false;
        }
        planRepository.deleteById(id);
        return true;
    }

    private PlanResponse aResponse(Plan plan) {
        List<SocioResponse> socios = plan.getSocios() == null
                ? List.of()
                : plan.getSocios().stream()
                .map(socio -> new SocioResponse(
                        socio.getId(),
                        socio.getNombre(),
                        plan.getId(),
                        plan.getNombre()
                ))
                .toList();

        return new PlanResponse(
                plan.getId(),
                plan.getNombre(),
                plan.getDuracionMeses(),
                plan.getPrecio(),
                socios
        );
    }
}