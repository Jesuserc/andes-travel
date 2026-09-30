package com.example.demo.service;

import com.example.demo.model.Paquete;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaqueteService {

    private final List<Paquete> listaPaquetes = new ArrayList<>();
    private Long siguienteId = 1L;

    public PaqueteService() {
        guardar(new Paquete(null, "Cusco & Machu Picchu Imperial", "5 Días / 4 Noches", 1890.0, "Entradas a Machu Picchu, Tren Expedition, Guía",
            "https://images.unsplash.com/photo-1526392060635-9d6019884377?q=80&w=800",
            "Explora la capital del Imperio Inca, el Valle Sagrado y la maravilla del mundo con todo organizado.",
            "Hotel Xima Cusco (4 estrellas)", true, true, "Bus turístico privado + Tren Perurail", "LATAM Airlines"));

        guardar(new Paquete(null, "Arequipa & Cañón del Colca", "3 Días / 2 Noches", 850.0, "Tour Colca, Mirador Cruz del Cóndor, City Tour",
            "https://images.unsplash.com/photo-1589802829985-817e51171b92?q=80&w=800",
            "Visita la Ciudad Blanca, el Monasterio de Santa Catalina y contempla el majestuoso vuelo del cóndor.",
            "Hotel Casa Andina Standard", false, true, "Minivan privada con aire acondicionado", "SKY Airline"));

        guardar(new Paquete(null, "Máncora & Playas del Norte", "4 Días / 3 Noches", 1200.0, "Avistamiento de tortugas, Noche de fogata",
            "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=800",
            "Disfruta del sol todo el año, relájate frente al mar y vive la mejor gastronomía marina del Perú.",
            "Arennas Máncora Resort", true, true, "Traslado privado Aeropuerto Tumbes/Piura", "LATAM Airlines"));

        guardar(new Paquete(null, "Tarapoto & Selva Mágica", "4 Días / 3 Noches", 1150.0, "Laguna Azul, Cataratas de Ahuashiyacu, Castillo de Lamas",
            "https://images.unsplash.com/photo-1516026672322-bc52d61a55d5?q=80&w=800",
            "Sumérgete en la Amazonía peruana, navegando en la Laguna Azul y disfrutando de sus paisajes exuberantes.",
            "Tucan Suites Tarapoto", false, true, "Bote a motor y Sprinter privada", "Star Perú"));

        guardar(new Paquete(null, "Paracas, Ica & Huacachina", "2 Días / 1 Noche", 490.0, "Islas Ballestas, Tubulares en las dunas, Sandboarding",
            "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?q=80&w=800",
            "Aventura pura en el desierto de Ica, paseo en buggy por las dunas y fauna marina en Paracas.",
            "Hotel San Agustín Paracas", false, true, "Bus Cruz del Sur Ejecutivo", "No requiere vuelo (Terrestre)"));

        guardar(new Paquete(null, "Puno, Lago Titicaca & Uros", "3 Días / 2 Noches", 980.0, "Islas Flotantes de los Uros, Isla Taquile con almuerzo",
            "https://images.unsplash.com/photo-1534447677768-be436bb09401?q=80&w=800",
            "Conoce el lago navegable más alto del mundo y comparte la cultura ancestral con sus comunidades.",
            "GHL Hotel Lago Titicaca", false, true, "Lancha rápida panorámica + Bus privado", "LATAM Airlines"));
    }

    public List<Paquete> listarTodos() { return listaPaquetes; }

    public Optional<Paquete> buscarPorId(Long id) {
        return listaPaquetes.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    /** Consulta: filtra por nombre, hotel, aerolínea, incluye o descripción. */
    public List<Paquete> buscar(String q) {
        if (q == null || q.isBlank()) return listaPaquetes;
        String t = q.trim().toLowerCase();
        return listaPaquetes.stream()
            .filter(p -> contiene(p.getNombre(), t) || contiene(p.getHotel(), t)
                      || contiene(p.getAerolinea(), t) || contiene(p.getIncluye(), t)
                      || contiene(p.getDescripcion(), t))
            .toList();
    }

    private boolean contiene(String texto, String t) {
        return texto != null && texto.toLowerCase().contains(t);
    }

    /** Crea (id null) o actualiza en la misma posición (id existente). */
    public void guardar(Paquete paquete) {
        if (paquete.getId() == null) {
            paquete.setId(siguienteId++);
            listaPaquetes.add(paquete);
            return;
        }
        for (int i = 0; i < listaPaquetes.size(); i++) {
            if (listaPaquetes.get(i).getId().equals(paquete.getId())) {
                listaPaquetes.set(i, paquete);
                return;
            }
        }
        listaPaquetes.add(paquete);
    }

    public void eliminar(Long id) {
        listaPaquetes.removeIf(p -> p.getId().equals(id));
    }

    // ---- Datos para gráficos ----
    public Map<String, Double> precioPorPaquete() {
        Map<String, Double> m = new LinkedHashMap<>();
        listaPaquetes.forEach(p -> m.put(p.getNombre(), p.getPrecio()));
        return m;
    }

    public Map<String, Long> cantidadPorAerolinea() {
        Map<String, Long> m = new LinkedHashMap<>();
        for (Paquete p : listaPaquetes) {
            String a = (p.getAerolinea() == null || p.getAerolinea().isBlank()) ? "Sin dato" : p.getAerolinea();
            m.merge(a, 1L, Long::sum);
        }
        return m;
    }
}
