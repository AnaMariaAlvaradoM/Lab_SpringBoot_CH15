package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.dto.PlanRequest;
import co.cuerpoactivo.cuerpo_activo_api.dto.PlanResponse;
import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
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

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<PlanResponse> listar() {
        return planService.buscarTodos().stream()
                .map(this::aRespuesta)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id)
                .map(plan -> ResponseEntity.ok(aRespuesta(plan)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlanResponse> crear(@RequestBody PlanRequest request) {
        Plan guardado = planService.guardar(aEntidad(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(aRespuesta(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> actualizar(@PathVariable Long id, @RequestBody PlanRequest request) {
        return planService.actualizar(id, aEntidad(request))
                .map(plan -> ResponseEntity.ok(aRespuesta(plan)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (planService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Plan aEntidad(PlanRequest request) {
        Plan plan = new Plan();
        plan.setNombre(request.nombre());
        plan.setDuracionMeses(request.duracionMeses());
        plan.setPrecio(request.precio());
        return plan;
    }

    private PlanResponse aRespuesta(Plan plan) {
        return new PlanResponse(plan.getId(), plan.getNombre(), plan.getDuracionMeses(), plan.getPrecio());
    }
}