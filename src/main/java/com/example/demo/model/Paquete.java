package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String duracion;
    private Double precio;
    private String incluye;

    @Column(length = 1000)
    private String imagenUrl;

    @Column(length = 1000)
    private String descripcion;

    private String destino;
    private boolean activo = true;
    private boolean destacado = false;
    private String categoria;
    private String duracionDias;

    public Paquete() {}

    public Paquete(Long id, String nombre, String duracion, Double precio, String incluye, String imagenUrl, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.incluye = incluye;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getIncluye() { return incluye; }
    public void setIncluye(String incluye) { this.incluye = incluye; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public boolean isDestacado() { return destacado; }
    public void setDestacado(boolean destacado) { this.destacado = destacado; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDuracionDias() { return duracionDias; }
    public void setDuracionDias(String duracionDias) { this.duracionDias = duracionDias; }
}