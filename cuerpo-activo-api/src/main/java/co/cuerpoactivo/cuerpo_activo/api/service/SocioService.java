package co.cuerpoactivo.cuerpo_activo.api.service;

import co.cuerpoactivo.cuerpo_activo.api.dto.SocioRequest;
import co.cuerpoactivo.cuerpo_activo.api.exception.RecursoNoEncontradoException;
import co.cuerpoactivo.cuerpo_activo.api.exception.ReglaDeNegocioException;
import co.cuerpoactivo.cuerpo_activo.api.model.Plan;
import co.cuerpoactivo.cuerpo_activo.api.model.Socio;
import co.cuerpoactivo.cuerpo_activo.api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo.api.repository.SocioRepository;
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
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el socio con id " + id
                ));
    }


    public Socio crear(SocioRequest request) {
        Optional<Plan> encontrado = planRepository.findById(request.planId());

        if (encontrado.isEmpty()) {
            throw new ReglaDeNegocioException(
                    "No existe el plan con id " + request.planId()
            );
        }

        Plan plan = encontrado.get();
        Socio socio = request.aEntidad(plan);

        return socioRepository.save(socio);
    }

}
