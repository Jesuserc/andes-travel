package com.example.demo.service;

import com.example.demo.model.Reserva;
import com.example.demo.repository.PaqueteRepository;
import com.example.demo.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PaqueteRepository paqueteRepository;

    public ReservaService(ReservaRepository reservaRepository, PaqueteRepository paqueteRepository) {
        this.reservaRepository = reservaRepository;
        this.paqueteRepository = paqueteRepository;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardar(Reserva reserva) {
        if (reserva.getEstado() == null) {
            reserva.setEstado("PENDIENTE");
        }
        
        if (reserva.getTotal() == null || reserva.getTotal() == 0) {
            paqueteRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(reserva.getPaqueteSeleccionado()))
                .findFirst()
                .ifPresent(p -> reserva.setTotal(p.getPrecio() * reserva.getCantidadPersonas()));
        }
        
        return reservaRepository.save(reserva);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}