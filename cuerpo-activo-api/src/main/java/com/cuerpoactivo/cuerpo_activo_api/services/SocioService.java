package com.cuerpoactivo.cuerpo_activo_api.services;

import com.cuerpoactivo.cuerpo_activo_api.dto.SocioRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.SocioResponse;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.exception.ReglaDeNegocioException;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.model.Socio;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import com.cuerpoactivo.cuerpo_activo_api.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<SocioResponse> listar() {
        List<Socio> socios = socioRepository.findAll();
        List<SocioResponse> respuesta = new ArrayList<>();

        for (Socio socio : socios) {
            respuesta.add(SocioResponse.desde(socio));
        }

        return respuesta;
    }

    public SocioResponse buscarPorId(Long id) {
        Optional<Socio> encontrado = socioRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("Socio no encontrado con id: " + id);
        }
        return SocioResponse.desde(encontrado.get());
    }

    public SocioResponse crear(SocioRequest datos) {
        Optional<Plan> planEncontrado = planRepository.findById(datos.planId());
        if (planEncontrado.isEmpty()) {
            throw new ReglaDeNegocioException("El plan con id " + datos.planId() + " no existe");
        }

        Socio nuevoSocio = datos.aEntidad(planEncontrado.get());
        nuevoSocio.setId(null);
        Socio guardado = socioRepository.save(nuevoSocio);
        return SocioResponse.desde(guardado);
    }
}