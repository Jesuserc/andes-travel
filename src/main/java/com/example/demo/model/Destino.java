package com.example.demo.model;

public class Destino {
    private Long id;
    private String ciudad;
    private String departamento;
    private String region;
    private String climaRecomendado;
    private boolean requiereTrekking;

    // Constructor 
    public Destino() {
    }

    // Constructor
    public Destino(Long id, String ciudad, String departamento, String region, String climaRecomendado, boolean requiereTrekking) {
        this.id = id;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.region = region;
        this.climaRecomendado = climaRecomendado;
        this.requiereTrekking = requiereTrekking;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getClimaRecomendado() {
        return climaRecomendado;
    }

    public void setClimaRecomendado(String climaRecomendado) {
        this.climaRecomendado = climaRecomendado;
    }

    public boolean isRequiereTrekking() {
        return requiereTrekking;
    }

    public void setRequiereTrekking(boolean requiereTrekking) {
        this.requiereTrekking = requiereTrekking;
    }
}
