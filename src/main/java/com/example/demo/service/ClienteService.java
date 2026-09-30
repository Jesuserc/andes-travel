package com.example.demo.service;

import com.example.demo.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private final List<Cliente> lista = new ArrayList<>();
    private Long siguienteId = 1L;

    public ClienteService() {
        guardar(new Cliente(null, "Juan Pérez", "juan@gmail.com", "987654321"));
        guardar(new Cliente(null, "María Torres", "maria@gmail.com", "955123456"));
    }

    public List<Cliente> listarTodos() { return lista; }

    public List<Cliente> buscar(String q) {
        if (q == null || q.isBlank()) return lista;
        String t = q.trim().toLowerCase();
        return lista.stream()
            .filter(c -> c.getNombre().toLowerCase().contains(t)
                      || c.getCorreo().toLowerCase().contains(t)
                      || (c.getTelefono() != null && c.getTelefono().contains(t)))
            .toList();
    }

    public void guardar(Cliente c) {
        c.setId(siguienteId++);
        lista.add(c);
    }

    public void eliminar(Long id) {
        lista.removeIf(c -> c.getId().equals(id));
    }

    /** Registra al cliente automáticamente cuando hace una reserva, si su correo aún no existe. */
    public void registrarSiNoExiste(String nombre, String correo) {
        boolean existe = lista.stream().anyMatch(c -> c.getCorreo().equalsIgnoreCase(correo));
        if (!existe) guardar(new Cliente(null, nombre, correo, ""));
    }
}
