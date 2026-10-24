package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ciudad;

    private String departamento;
    private String climaRecomendado;
    private boolean requiereTrekking;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    public Destino() {}

    public Destino(Long id, String ciudad, String departamento, String climaRecomendado, boolean requiereTrekking) {
        this.id = id;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.climaRecomendado = climaRecomendado;
        this.requiereTrekking = requiereTrekking;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getClimaRecomendado() { return climaRecomendado; }
    public void setClimaRecomendado(String climaRecomendado) { this.climaRecomendado = climaRecomendado; }

    public boolean isRequiereTrekking() { return requiereTrekking; }
    public void setRequiereTrekking(boolean requiereTrekking) { this.requiereTrekking = requiereTrekking; }

    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}