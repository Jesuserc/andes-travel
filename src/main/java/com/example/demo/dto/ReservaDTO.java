package com.example.demo.dto;

import java.time.LocalDate;

public class ReservaDTO {
    private Long idReserva;
    private String nombreCliente;
    private String emailCliente;
    private String nombrePaquete;
    private LocalDate fechaViaje;
    private Integer cantidadPersonas;
    private Double precioTotal;
    private String estado;

    public ReservaDTO(Long idReserva, String nombreCliente, String emailCliente, 
                      String nombrePaquete, LocalDate fechaViaje, 
                      Integer cantidadPersonas, Double precioTotal, String estado) {
        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.nombrePaquete = nombrePaquete;
        this.fechaViaje = fechaViaje;
        this.cantidadPersonas = cantidadPersonas;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getNombrePaquete() { return nombrePaquete; }
    public void setNombrePaquete(String nombrePaquete) { this.nombrePaquete = nombrePaquete; }

    public LocalDate getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }

    public Integer getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(Integer cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}