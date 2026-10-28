package com.example.demo.controller;

import com.example.demo.model.Reserva;
import com.example.demo.service.ClienteService;
import com.example.demo.service.PaqueteService;
import com.example.demo.service.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {

    private final PaqueteService paqueteService;
    private final ReservaService reservaService;
    private final ClienteService clienteService;

    public ClienteController(PaqueteService paqueteService, 
        ReservaService reservaService, 
        ClienteService clienteService) {
        this.paqueteService = paqueteService;
        this.reservaService = reservaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public String inicio(Model model) { 
        model.addAttribute("paquetes", paqueteService.listarTodos());
        return "cliente/index"; 
    }

    @GetMapping("/destinos")
    public String destinos(Model model) {
        model.addAttribute("paquetes", paqueteService.listarTodos());
        return "cliente/destinos";
    }

    @GetMapping("/paquetes")
    public String paquetes(Model model) {
        model.addAttribute("paquetes", paqueteService.listarTodos());
        return "cliente/paquetes";
    }

    @GetMapping("/confirmacion")
    public String confirmacion() {
        return "cliente/confirmacion";
    }

    @GetMapping("/nosotros")
    public String nosotros() { return "cliente/nosotros"; }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("paquetes", paqueteService.listarTodos());
        model.addAttribute("reserva", new Reserva());
        return "cliente/contacto";
    }

    @PostMapping("/reservar")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva, RedirectAttributes redirectAttributes) {
        reservaService.guardar(reserva);
        return "redirect:/confirmacion";
    }
}