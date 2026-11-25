package com.cuerpoactivo.cuerpo_activo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuerpoactivo.cuerpo_activo_api.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {
}
