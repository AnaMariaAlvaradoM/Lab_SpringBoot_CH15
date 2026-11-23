package co.cuerpoactivo.cuerpo_activo_api.model;

import jakarta.persistence.*;

@Entity
@Table(name="planes")

public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int duracionMeses;
    private int precio;

    public Plan(){}

    public Plan(Long id, String nombre, int duracionMeses, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.duracionMeses = duracionMeses;
        this.precio = precio;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }
    public int getDuracionMeses() {
        return duracionMeses;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getPrecio() {
        return precio;
    }
}
