package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> obtenerTodos() {
        return planService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> obtenerPorId(@PathVariable Long id) {
        PlanResponse plan = planService.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("Plan no encontrado con id " + id));
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crearPlan(@Valid @RequestBody PlanRequest request) {
        PlanResponse creado = planService.guardar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizarPlan(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        PlanResponse actualizado = planService.actualizar(id, request)
                .orElseThrow(() -> new NoSuchElementException("Plan no encontrado con id " + id));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        if (!planService.eliminar(id)) {
            throw new NoSuchElementException("Plan no encontrado con id " + id);
        }
        return ResponseEntity.noContent().build();
    }
}