package com.cuerpoactivo.cuerpo_activo_api.model;

public class Plan {
    private Long id;
    private String nombre;
    private int duracionMeses;
    private int precio;

    // Constructor vacío
    public Plan() {
    }

    // Constructor con todos los atributos
    public Plan(Long id, String nombre, int duracionMeses, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.duracionMeses = duracionMeses;
        this.precio = precio;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public int getPrecio() {
        return precio;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
