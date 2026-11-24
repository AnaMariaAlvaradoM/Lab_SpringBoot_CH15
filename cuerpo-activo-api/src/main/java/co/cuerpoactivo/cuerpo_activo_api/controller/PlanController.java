package co.cuerpoactivo.cuerpo_activo_api.controller;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.service.PlanService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

//    private final List<Plan> planes = new ArrayList<>();
//
//    public PlanController() {
//        planes.add(new Plan(1L, "Plan mensual", 1, 120000));
//        planes.add(new Plan(2L, "Plan trimestral", 3, 330000));
//        planes.add(new Plan(3L, "Plan anual", 12, 1200000));
//    }
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> listar() {
//        return planes;
        return planService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPorId(@PathVariable Long id) {
//        for (Plan plan: planes) {
//            if (plan.getId().equals(id)) {
//                return plan;
//            }
//        }
//        return null;
        Optional<Plan> encontrado = planService.buscarPorId(id);
        if (encontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encontrado.get());
    }

    @PostMapping
    public ResponseEntity<Plan> crear(@RequestBody Plan nuevo) {
//        nuevo.setId(planes.size() + 1L);
//        planes.add(nuevo);
//        return nuevo;

        Plan creado = planService.crear(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

}
