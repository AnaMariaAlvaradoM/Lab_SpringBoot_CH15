
package co.cuerpoactivo.cuerpo_activo_api.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "plan")
public class Plan {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "duracion_meses")
    private int duracionMeses;

    private int precio;

    // Relación: un plan puede tener muchos socios
    @OneToMany(mappedBy = "plan")
    private List<Socio> socios = new ArrayList<>();

    // Constructor vacío
    public Plan() {
    }

    // Constructor con parámetros
    public Plan(Long id, String nombre, int duracionMeses, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.duracionMeses = duracionMeses;
        this.precio = precio;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }
}
