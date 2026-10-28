package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT new com.example.demo.dto.ReservaDTO(" +
           "r.id, " +
           "COALESCE(r.nombreCliente, 'Sin Nombre'), " +
           "COALESCE(r.correoCliente, 'sin_correo@mail.com'), " +
           "COALESCE(r.paqueteSeleccionado, 'Sin Paquete'), " +
           "r.fechaViaje, " +
           "r.cantidadPersonas, " +
           "CAST(COALESCE(r.cantidadPersonas, 1) * 450.0 AS double), " +
           "COALESCE(r.estado, 'PENDIENTE')) " +
           "FROM Reserva r")
    List<ReservaDTO> obtenerReservasDetalladas();
}