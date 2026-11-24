package co.cuerpoactivo.cuerpo_activo_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "socio")
public class Socio {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public Socio() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
