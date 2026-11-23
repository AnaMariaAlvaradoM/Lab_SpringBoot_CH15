package co.cuerpoactivo.cuerpo_activo_api.service;

import co.cuerpoactivo.cuerpo_activo_api.model.Plan;
import co.cuerpoactivo.cuerpo_activo_api.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    public final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> listar(){
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPorId(Long id){
        return planRepository.findById(id);
    }

    public Plan crear(Plan nuevo){
        nuevo.setId(null);
        return planRepository.save(nuevo);
    }
}
