package cuerpo_activo_api.repository;

import cuerpo_activo_api.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioRepository extends JpaRepository<Socio, Long> {
}