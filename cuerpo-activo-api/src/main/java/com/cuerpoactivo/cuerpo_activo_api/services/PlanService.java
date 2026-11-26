package com.cuerpoactivo.cuerpo_activo_api.services;

import com.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import com.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import com.cuerpoactivo.cuerpo_activo_api.exception.RecursoNoEncontradoException;
import com.cuerpoactivo.cuerpo_activo_api.model.Plan;
import com.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanResponse> listar() {
        List<Plan> planes = planRepository.findAll();
        List<PlanResponse> respuesta = new ArrayList<>();

        for (Plan plan : planes) {
            respuesta.add(PlanResponse.desde(plan));
        }

        return respuesta;
    }

    public PlanResponse buscarPorId(Long id) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("Plan no encontrado con id: " + id);
        }
        return PlanResponse.desde(encontrado.get());
    }

    public PlanResponse crear(PlanRequest datos) {
        Plan nuevo = datos.aEntidad();
        nuevo.setId(null);
        Plan guardado = planRepository.save(nuevo);
        return PlanResponse.desde(guardado);
    }

    public PlanResponse actualizar(Long id, PlanRequest datos) {
        Optional<Plan> encontrado = planRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new RecursoNoEncontradoException("Plan no encontrado con id: " + id);
        }

        Plan plan = encontrado.get();
        plan.setNombre(datos.nombre());
        plan.setDuracionMeses(datos.duracionMeses());
        plan.setPrecio(datos.precio());

        Plan actualizado = planRepository.save(plan);
        return PlanResponse.desde(actualizado);
    }

    public void eliminar(Long id) {
        if (!planRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Plan no encontrado con id: " + id);
        }
        planRepository.deleteById(id);
    }
}