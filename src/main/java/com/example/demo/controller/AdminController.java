package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.model.Paquete;
import com.example.demo.model.Reserva;
import com.example.demo.service.ClienteService;
import com.example.demo.service.PaqueteService;
import com.example.demo.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class AdminController {

    private final PaqueteService paqueteService;
    private final ReservaService reservaService;
    private final ClienteService clienteService;

    public AdminController(PaqueteService paqueteService, ReservaService reservaService, ClienteService clienteService) {
        this.paqueteService = paqueteService;
        this.reservaService = reservaService;
        this.clienteService = clienteService;
    }

    // ---------- Login / Logout ----------
    @GetMapping("/login")
    public String login() { return "admin/login"; }

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
        model.addAttribute("paquete", paqueteService.buscarPorId(id).orElse(new Paquete()));
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

    // ---------- Reservas ----------
    @GetMapping("/admin/reservas")
    public String verReservas(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("reservas", reservaService.buscar(q));
        model.addAttribute("paquetes", paqueteService.listarTodos());
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("q", q);
        return "admin/reservas";
    }

    @PostMapping("/admin/reservas/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva, RedirectAttributes ra) {
        reservaService.guardarReserva(reserva);
        clienteService.registrarSiNoExiste(reserva.getNombreCliente(), reserva.getCorreoCliente());
        ra.addFlashAttribute("ok", "Reserva registrada correctamente.");
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
        model.addAttribute("clientes", clienteService.buscar(q));
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("q", q);
        return "admin/clientes";
    }

    @PostMapping("/admin/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        clienteService.guardar(cliente);
        ra.addFlashAttribute("ok", "Cliente registrado correctamente.");
        return "redirect:/admin/clientes";
    }

    @PostMapping("/admin/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes ra) {
        clienteService.eliminar(id);
        ra.addFlashAttribute("ok", "Cliente eliminado.");
        return "redirect:/admin/clientes";
    }

    // ---------- Gráficos (2 páginas) ----------
    // Antes /admin/reportes apuntaba a una plantilla inexistente (error 500). Se mantiene la ruta y redirige.
    @GetMapping("/admin/reportes")
    public String reportes() { return "redirect:/admin/graficos/paquetes"; }

    @GetMapping("/admin/graficos/paquetes")
    public String graficosPaquetes(Model model) {
        Map<String, Double> precios = paqueteService.precioPorPaquete();
        Map<String, Long> aerolineas = paqueteService.cantidadPorAerolinea();
        model.addAttribute("preciosLabels", new ArrayList<>(precios.keySet()));
        model.addAttribute("preciosValues", new ArrayList<>(precios.values()));
        model.addAttribute("aeroLabels", new ArrayList<>(aerolineas.keySet()));
        model.addAttribute("aeroValues", new ArrayList<>(aerolineas.values()));
        return "admin/graficos-paquetes";
    }

    @GetMapping("/admin/graficos/reservas")
    public String graficosReservas(Model model) {
        Map<String, Long> porFecha = reservaService.reservasPorFecha();
        Map<String, Long> porPaquete = reservaService.reservasPorPaquete();
        Map<String, Integer> viajeros = reservaService.viajerosPorPaquete();
        model.addAttribute("fechaLabels", new ArrayList<>(porFecha.keySet()));
        model.addAttribute("fechaValues", new ArrayList<>(porFecha.values()));
        model.addAttribute("paqLabels", new ArrayList<>(porPaquete.keySet()));
        model.addAttribute("paqValues", new ArrayList<>(porPaquete.values()));
        model.addAttribute("viajLabels", new ArrayList<>(viajeros.keySet()));
        model.addAttribute("viajValues", new ArrayList<>(viajeros.values()));
        return "admin/graficos-reservas";
    }
}
