package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    // private final List<Plan> planes = new ArrayList<>();
    private final PlanService planService;

    public PlanController(PlanService planService) {this.planService = planService;}

        //planes.add(new Plan(1L, "Plan mensual", 1, 120000));
        //planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
        //planes.add(new Plan(3L, "Plan anual", 12, 1200000));

    @GetMapping
    public List<Plan> listar() {
        return planService.listar();
    }

    @GetMapping("/{id}")
    public Plan buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public Plan crear(@RequestBody Plan nuevo) {
        return planService.crear(nuevo);
    }
}