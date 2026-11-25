package co.cuerpoactivo.cuerpo_activo_api.repository;

import co.cuerpoactivo.cuerpo_activo_api.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio,Long> {
}
