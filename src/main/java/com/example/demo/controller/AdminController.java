package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.ReservaDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Paquete;
import com.example.demo.model.Reserva;
import com.example.demo.service.ClienteService;
import com.example.demo.service.PaqueteService;
import com.example.demo.service.ReservaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final PaqueteService paqueteService;
    private final ReservaService reservaService;
    private final ClienteService clienteService;

    public AdminController(PaqueteService paqueteService, 
                           ReservaService reservaService, 
                           ClienteService clienteService) {
        this.paqueteService = paqueteService;
        this.reservaService = reservaService;
        this.clienteService = clienteService;
    }

    // ---------- Login / Logout ----------
    @GetMapping("/login")
    public String login() { 
        return "admin/login"; 
    }

    @PostMapping("/login")
    public String validarLogin(@RequestParam String usuario, @RequestParam String password,
                               HttpSession session, Model model) {
        if ("admin".equals(usuario) && "1234".equals(password)) {
            session.setAttribute("admin", usuario);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------- Paquetes: ingreso, listado, consulta, edición, eliminación ----------
    @GetMapping("/admin/dashboard")
    public String dashboard(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("paquetes", paqueteService.buscar(q));
        model.addAttribute("paquete", new Paquete());
        model.addAttribute("q", q);
        return "admin/dashboard";
    }

    @GetMapping("/admin/paquetes/editar/{id}")
    public String editarPaquete(@PathVariable Long id, Model model) {
        model.addAttribute("paquetes", paqueteService.listarTodos());
        model.addAttribute("paquete", paqueteService.obtenerPorId(id).orElse(new Paquete()));
        model.addAttribute("editando", true);
        return "admin/dashboard";
    }

    @PostMapping("/admin/paquetes/guardar")
    public String guardarPaquete(@ModelAttribute Paquete paquete, RedirectAttributes ra) {
        boolean nuevo = paquete.getId() == null;
        paqueteService.guardar(paquete);
        ra.addFlashAttribute("ok", nuevo ? "Paquete registrado correctamente." : "Paquete actualizado correctamente.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/paquetes/eliminar/{id}")
    public String eliminarPaquete(@PathVariable Long id, RedirectAttributes ra) {
        paqueteService.eliminar(id);
        ra.addFlashAttribute("ok", "Paquete eliminado.");
        return "redirect:/admin/dashboard";
    }

    // ---------- Reservas (Utilizando DTOs) ----------
    // ---------- Reservas (Con DTOs y Filtro Funcional) ----------
    @GetMapping("/admin/reservas")
    public String verReservas(@RequestParam(required = false) String q, 
                            @RequestParam(required = false) String paquete, 
                            Model model) {
        
        List<ReservaDTO> dtoList = reservaService.listarReservasDTO();

        // 1. Filtro desplegable por Paquete Seleccionado
        if (paquete != null && !paquete.trim().isEmpty()) {
            String paqLower = paquete.toLowerCase().trim();
            dtoList = dtoList.stream()
                    .filter(r -> r.getNombrePaquete() != null && r.getNombrePaquete().toLowerCase().contains(paqLower))
                    .collect(Collectors.toList());
        }

        // 2. Filtro de Búsqueda libre
        if (q != null && !q.trim().isEmpty()) {
            String query = q.toLowerCase().trim();
            dtoList = dtoList.stream()
                    .filter(r -> (r.getNombreCliente() != null && r.getNombreCliente().toLowerCase().contains(query))
                              || (r.getEmailCliente() != null && r.getEmailCliente().toLowerCase().contains(query))
                              || (r.getNombrePaquete() != null && r.getNombrePaquete().toLowerCase().contains(query)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("reservasDTO", dtoList);
        model.addAttribute("paquetes", paqueteService.listarTodos());
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("q", q);
        model.addAttribute("paqueteSeleccionado", paquete);
        
        return "admin/reservas";
    }

    @PostMapping("/admin/reservas/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva, RedirectAttributes ra) {
        reservaService.guardar(reserva);
        ra.addFlashAttribute("ok", "Reserva registrada correctamente.");
        return "redirect:/admin/reservas";
    }

    @PostMapping("/admin/reservas/atender/{id}")
    public String atenderReserva(@PathVariable Long id, RedirectAttributes ra) {
        Optional<Reserva> optReserva = reservaService.obtenerPorId(id);
        if (optReserva.isPresent()) {
            Reserva reserva = optReserva.get();
            reserva.setEstado("ATENDIDA");
            reservaService.guardar(reserva);

            // Sincronización automática con la tabla 'clientes'
            if (reserva.getCorreoCliente() != null && !reserva.getCorreoCliente().isEmpty()) {
                boolean existe = clienteService.listarTodos().stream()
                        .anyMatch(c -> c.getCorreo() != null && c.getCorreo().equalsIgnoreCase(reserva.getCorreoCliente()));
                
                if (!existe) {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setNombre(reserva.getNombreCliente());
                    nuevoCliente.setCorreo(reserva.getCorreoCliente());
                    nuevoCliente.setTelefono(reserva.getTelefonoCliente());
                    clienteService.guardar(nuevoCliente);
                }
            }

            ra.addFlashAttribute("ok", "Reserva #" + id + " marcada como ATENDIDA y cliente sincronizado en el Directorio.");
        }
        return "redirect:/admin/reservas";
    }

    @PostMapping("/admin/reservas/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id, RedirectAttributes ra) {
        reservaService.eliminar(id);
        ra.addFlashAttribute("ok", "Reserva eliminada.");
        return "redirect:/admin/reservas";
    }

    // ---------- Clientes ----------
    @GetMapping("/admin/clientes")
    public String verClientes(@RequestParam(required = false) String q, Model model) {
        List<Cliente> lista = clienteService.listarTodos();

        // Filtro de búsqueda por nombre, correo, teléfono o DNI
        if (q != null && !q.trim().isEmpty()) {
            String query = q.toLowerCase().trim();
            lista = lista.stream()
                    .filter(c -> (c.getNombre() != null && c.getNombre().toLowerCase().contains(query))
                              || (c.getCorreo() != null && c.getCorreo().toLowerCase().contains(query))
                              || (c.getTelefono() != null && c.getTelefono().toLowerCase().contains(query))
                              || (c.getDni() != null && c.getDni().toLowerCase().contains(query)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("clientes", lista);
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("q", q);
        return "admin/clientes";
    }

    @PostMapping("/admin/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        clienteService.guardar(cliente);
        ra.addFlashAttribute("ok", "Cliente guardado correctamente.");
        return "redirect:/admin/clientes";
    }

    @PostMapping("/admin/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes ra) {
        clienteService.eliminar(id);
        ra.addFlashAttribute("ok", "Cliente eliminado.");
        return "redirect:/admin/clientes";
    }

    // ---------- Gráficos (2 páginas) ----------
    @GetMapping("/admin/reportes")
    public String reportes() { 
        return "redirect:/admin/graficos/paquetes"; 
    }

    @GetMapping("/admin/graficos/paquetes")
    public String graficosPaquetes(Model model) {
        List<Paquete> paquetes = paqueteService.listarTodos();

        // Nombres y Precios
        List<String> preciosLabels = paquetes.stream().map(Paquete::getNombre).collect(Collectors.toList());
        List<Double> preciosValues = paquetes.stream().map(Paquete::getPrecio).collect(Collectors.toList());

        // Conteo de Paquetes por Destino
        Map<String, Long> destinoMap = paquetes.stream()
                .filter(p -> p.getDestino() != null && !p.getDestino().isEmpty())
                .collect(Collectors.groupingBy(Paquete::getDestino, Collectors.counting()));

        List<String> aeroLabels = new ArrayList<>(destinoMap.keySet());
        List<Long> aeroValues = new ArrayList<>(destinoMap.values());

        model.addAttribute("preciosLabels", preciosLabels);
        model.addAttribute("preciosValues", preciosValues);
        model.addAttribute("aeroLabels", aeroLabels);
        model.addAttribute("aeroValues", aeroValues);

        return "admin/graficos-paquetes";
    }

    @GetMapping("/admin/graficos/reservas")
    public String graficosReservas(Model model) {
        List<Reserva> reservas = reservaService.listarTodas();

        // Agrupación por Fecha de Viaje
        Map<String, Long> fechaMap = reservas.stream()
                .filter(r -> r.getFechaViaje() != null)
                .collect(Collectors.groupingBy(r -> r.getFechaViaje().toString(), Collectors.counting()));

        List<String> fechaLabels = new ArrayList<>(fechaMap.keySet());
        List<Long> fechaValues = new ArrayList<>(fechaMap.values());

        // Agrupación de Reservas por Paquete
        Map<String, Long> paqMap = reservas.stream()
                .filter(r -> r.getPaqueteSeleccionado() != null)
                .collect(Collectors.groupingBy(Reserva::getPaqueteSeleccionado, Collectors.counting()));

        List<String> paqLabels = new ArrayList<>(paqMap.keySet());
        List<Long> paqValues = new ArrayList<>(paqMap.values());

        // Cantidad de Viajeros por Paquete
        Map<String, Integer> viajMap = reservas.stream()
                .filter(r -> r.getPaqueteSeleccionado() != null && r.getCantidadPersonas() != null)
                .collect(Collectors.groupingBy(
                        Reserva::getPaqueteSeleccionado,
                        Collectors.summingInt(Reserva::getCantidadPersonas)
                ));

        List<String> viajLabels = new ArrayList<>(viajMap.keySet());
        List<Integer> viajValues = new ArrayList<>(viajMap.values());

        model.addAttribute("fechaLabels", fechaLabels);
        model.addAttribute("fechaValues", fechaValues);
        model.addAttribute("paqLabels", paqLabels);
        model.addAttribute("paqValues", paqValues);
        model.addAttribute("viajLabels", viajLabels);
        model.addAttribute("viajValues", viajValues);

        return "admin/graficos-reservas";
    }
}