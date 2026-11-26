
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

@Service
public class SocioService {

    private final SocioRepository socioRepository;
    private final PlanRepository planRepository;

    public SocioService(
            SocioRepository socioRepository,
            PlanRepository planRepository
    ) {
        this.socioRepository = socioRepository;
        this.planRepository = planRepository;
    }

    public List<Socio> listar() {
        return socioRepository.findAll();
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe el socio con id " + id
                        )
                );
    }

    public Socio crear(SocioRequest request) {
        Plan plan = planRepository.findById(request.planId()).orElseThrow(() ->
                new ReglaDeNegocioException(
                        "No existe el plan con id " + request.planId()
                )
        );

        Socio nuevo = request.aEntidad(plan);

        return socioRepository.save(nuevo);
    }

    public Socio actualizar(Long id, SocioRequest request) {
        Socio encontrado = socioRepository.findById(id);
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ReglaDeNegocioException(
                        "No existe el plan con id " + request.planId()
                ));

        encontrado.setNombre(request.nombre());
        encontrado.setPlan(plan);

        return socioRepository.save(encontrado);
    }

    public boolean eliminar(Long id) {
        Socio encontrado = buscarPorId(id);
        socioRepository.delete(encontrado);
    }
}