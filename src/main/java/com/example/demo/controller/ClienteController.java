package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Paquete;
import com.example.demo.model.Reserva;
import com.example.demo.service.ClienteService;
import com.example.demo.service.PaqueteService;
import com.example.demo.service.ReservaService;

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
    public String destinos(@RequestParam(required = false) String destino, Model model) {
        List<Paquete> lista = (destino != null && !destino.isEmpty()) 
                ? paqueteService.listarPorDestino(destino) 
                : paqueteService.listarTodos();

        model.addAttribute("paquetes", lista);
        model.addAttribute("destinoSeleccionado", destino);
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