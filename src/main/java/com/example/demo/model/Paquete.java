package com.example.demo.model;

public class Paquete {
    private Long id;
    private String nombre;
    private String duracion;
    private double precio;
    private String incluye;
    private String imagenUrl;
    private String descripcion;
    
    // Nuevos campos detallados
    private String hotel;
    private boolean todoIncluido;
    private boolean desayuno;
    private String movilidad;
    private String aerolinea;

    public Paquete() {}

    public Paquete(Long id, String nombre, String duracion, double precio, String incluye, String imagenUrl, String descripcion, String hotel, boolean todoIncluido, boolean desayuno, String movilidad, String aerolinea) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
        this.incluye = incluye;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.hotel = hotel;
        this.todoIncluido = todoIncluido;
        this.desayuno = desayuno;
        this.movilidad = movilidad;
        this.aerolinea = aerolinea;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getIncluye() { return incluye; }
    public void setIncluye(String incluye) { this.incluye = incluye; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getHotel() { return hotel; }
    public void setHotel(String hotel) { this.hotel = hotel; }

    public boolean isTodoIncluido() { return todoIncluido; }
    public void setTodoIncluido(boolean todoIncluido) { this.todoIncluido = todoIncluido; }

    public boolean isDesayuno() { return desayuno; }
    public void setDesayuno(boolean desayuno) { this.desayuno = desayuno; }

    public String getMovilidad() { return movilidad; }
    public void setMovilidad(String movilidad) { this.movilidad = movilidad; }

    public String getAerolinea() { return aerolinea; }
    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }
}