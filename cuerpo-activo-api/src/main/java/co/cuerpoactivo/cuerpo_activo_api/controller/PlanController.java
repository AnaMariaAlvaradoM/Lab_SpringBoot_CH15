package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> listarPlanes(){
        return planService.listar();
    }

    @GetMapping("/{id}")
    public Plan obtenerPlan(@PathVariable Long id){

        return planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan crearPlan(@RequestBody Plan nuevoPlan){
        return planService.crear(nuevoPlan);
    }


}
