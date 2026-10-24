package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "itinerarios")
public class Itinerario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer diaNumero;
    private String tituloDia;

    @Column(length = 1000)
    private String descripcionDetallada;

    @ManyToOne
    @JoinColumn(name = "paquete_id")
    private Paquete paquete;

    public Itinerario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getDiaNumero() { return diaNumero; }
    public void setDiaNumero(Integer diaNumero) { this.diaNumero = diaNumero; }
    public String getTituloDia() { return tituloDia; }
    public void setTituloDia(String tituloDia) { this.tituloDia = tituloDia; }
    public String getDescripcionDetallada() { return descripcionDetallada; }
    public void setDescripcionDetallada(String descripcionDetallada) { this.descripcionDetallada = descripcionDetallada; }
    public Paquete getPaquete() { return paquete; }
    public void setPaquete(Paquete paquete) { this.paquete = paquete; }
}