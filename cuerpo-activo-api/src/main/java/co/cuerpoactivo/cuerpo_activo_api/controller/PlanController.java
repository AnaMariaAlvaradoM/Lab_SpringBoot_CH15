package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    public final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // Responde la lista
    @GetMapping
    public List<Plan> list() {
        return planService.listar();
    }

    //Responde el plan con ese id
    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id){
        return  planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevo){
        return planService.crear(nuevo);
    }
}