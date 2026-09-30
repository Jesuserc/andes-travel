package com.example.demo.service;

import com.example.demo.model.Reserva;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ReservaService {

    private final List<Reserva> listaReservas = new ArrayList<>();
    private Long siguienteId = 1L;

    public ReservaService() {
        sembrar("Juan Pérez", "juan@gmail.com", "Cusco & Machu Picchu Imperial", 2, "Consulta para octubre", 6);
        sembrar("María Torres", "maria@gmail.com", "Arequipa & Cañón del Colca", 3, "Viajamos en familia", 5);
        sembrar("Luis Ramos", "luis@hotmail.com", "Cusco & Machu Picchu Imperial", 4, "Aniversario", 5);
        sembrar("Ana Quispe", "ana@gmail.com", "Máncora & Playas del Norte", 2, "Luna de miel", 3);
        sembrar("Carlos Díaz", "carlos@gmail.com", "Paracas, Ica & Huacachina", 5, "Grupo de amigos", 2);
        sembrar("Rosa Vega", "rosa@gmail.com", "Puno, Lago Titicaca & Uros", 2, "", 1);
        sembrar("Pedro Soto", "pedro@gmail.com", "Cusco & Machu Picchu Imperial", 1, "Viaje solo", 0);
    }

    private void sembrar(String n, String c, String p, int v, String m, int diasAtras) {
        Reserva r = new Reserva(null, n, c, p, v, m);
        r.setFecha(LocalDate.now().minusDays(diasAtras));
        guardarReserva(r);
    }

    public List<Reserva> listarTodas() { return listaReservas; }

    /** Consulta por cliente, correo, paquete o mensaje. */
    public List<Reserva> buscar(String q) {
        if (q == null || q.isBlank()) return listaReservas;
        String t = q.trim().toLowerCase();
        return listaReservas.stream()
            .filter(r -> contiene(r.getNombreCliente(), t) || contiene(r.getCorreoCliente(), t)
                      || contiene(r.getNombrePaquete(), t) || contiene(r.getMensaje(), t))
            .toList();
    }

    private boolean contiene(String texto, String t) {
        return texto != null && texto.toLowerCase().contains(t);
    }

    public void guardarReserva(Reserva reserva) {
        reserva.setId(siguienteId++);
        if (reserva.getFecha() == null) reserva.setFecha(LocalDate.now());
        listaReservas.add(reserva);
    }

    public void eliminar(Long id) {
        listaReservas.removeIf(r -> r.getId().equals(id));
    }

    // ---- Datos para gráficos ----
    public Map<String, Long> reservasPorFecha() {
        Map<String, Long> m = new LinkedHashMap<>();
        Map<LocalDate, Long> ordenado = new TreeMap<>();
        listaReservas.forEach(r -> ordenado.merge(r.getFecha(), 1L, Long::sum));
        ordenado.forEach((f, n) -> m.put(f.toString(), n));
        return m;
    }

    public Map<String, Long> reservasPorPaquete() {
        Map<String, Long> m = new LinkedHashMap<>();
        listaReservas.forEach(r -> m.merge(r.getNombrePaquete(), 1L, Long::sum));
        return m;
    }

    public Map<String, Integer> viajerosPorPaquete() {
        Map<String, Integer> m = new LinkedHashMap<>();
        listaReservas.forEach(r -> m.merge(r.getNombrePaquete(), r.getNumeroViajeros(), Integer::sum));
        return m;
    }
}
