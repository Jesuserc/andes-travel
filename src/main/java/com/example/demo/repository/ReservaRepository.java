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
           "r.id, c.nombre, c.email, p.nombre, r.fechaViaje, r.cantidadPersonas, (p.precio * r.cantidadPersonas)) " +
           "FROM Reserva r JOIN r.cliente c JOIN r.paquete p")
    List<ReservaDTO> obtenerReservasDetalladas();
}