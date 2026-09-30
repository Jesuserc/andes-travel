package com.example.demo.model;

import java.time.LocalDate;

public class Reserva {
    private Long id;
    private String nombreCliente;
    private String correoCliente;
    private String nombrePaquete;
    private int numeroViajeros;
    private String mensaje;
    private LocalDate fecha;

    // Constructor vacío
    public Reserva() {}

    // Constructor con 6 parámetros (sin fecha inicial)
    public Reserva(Long id, String nombreCliente, String correoCliente, String nombrePaquete, int numeroViajeros, String mensaje) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.nombrePaquete = nombrePaquete;
        this.numeroViajeros = numeroViajeros;
        this.mensaje = mensaje;
    }

    public Reserva(Long id, String nombreCliente, String correoCliente, String nombrePaquete, int numeroViajeros, String mensaje, LocalDate fecha) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.nombrePaquete = nombrePaquete;
        this.numeroViajeros = numeroViajeros;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public String getNombrePaquete() { return nombrePaquete; }
    public void setNombrePaquete(String nombrePaquete) { this.nombrePaquete = nombrePaquete; }

    public int getNumeroViajeros() { return numeroViajeros; }
    public void setNumeroViajeros(int numeroViajeros) { this.numeroViajeros = numeroViajeros; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}