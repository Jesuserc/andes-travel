package com.example.demo.service;

import com.example.demo.model.Paquete;
import com.example.demo.repository.PaqueteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaqueteService {

    private final PaqueteRepository paqueteRepository;

    public PaqueteService(PaqueteRepository paqueteRepository) {
        this.paqueteRepository = paqueteRepository;
    }

    // Carga de datos iniciales a PostgreSQL si la tabla está vacía
    @PostConstruct
    public void initData() {
        if (paqueteRepository.count() == 0) {
            paqueteRepository.save(new Paquete(null, "Cusco Mágico", "4 Días / 3 Noches", 1200.0, 
                "Hotel, Pasajes, Tour a Machu Picchu", 
                "https://elcomercio.pe/resizer/v2/HI232NAE45BZ3OK2SF7TP3J42M.jpg?auth=6a5b4a15e0fd7814fcf29e36bf533e48fe219e920959929df6b959c899111192&width=980&height=653&quality=75&smart=true", 
                "Explora la capital del Imperio Inca con visitas guiadas a Sacsayhuamán y Machu Picchu."));

            paqueteRepository.save(new Paquete(null, "Arequipa y Cañón del Colca", "3 Días / 2 Noches", 850.0, 
                "Hotel, Transporte, Guía", 
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0c/91/2b/4f/photo0jpg.jpg?w=1000&h=-1&s=1", 
                "Descubre la Ciudad Blanca, admira el vuelo del cóndor y disfruta de su gastronomía única."));

            paqueteRepository.save(new Paquete(null, "Selva de Tarapoto", "4 Días / 3 Noches", 950.0, 
                "Lodge, Excursiones, Comidas", 
                "https://cumaceba.com/wp-content/uploads/2024/02/optimized_4687-1.jpg", 
                "Sumérgete en la Amazonía peruana con visitas a la Laguna Azul y cataratas espectaculares."));

            paqueteRepository.save(new Paquete(null, "Paracas, Ica y Huacachina", "2 Días / 1 Noche", 450.0, 
                "Transporte, Buggies, Sandboard", 
                "https://machupicchuviajesperu.com/wp-content/uploads/2025/02/oasis-de-Huacachina-ica-paracas-nazca.webp", 
                "Aventura pura en el oasis del desierto, paseo en lancha a las Islas Ballestas y tubulares."));
        }
    }

    public List<Paquete> listarTodos() {
        return paqueteRepository.findAll();
    }

    public List<Paquete> buscar(String q) {
        if (q != null && !q.trim().isEmpty()) {
            return paqueteRepository.findAll().stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(q.toLowerCase()) ||
                                 p.getDescripcion().toLowerCase().contains(q.toLowerCase()))
                    .toList();
        }
        return paqueteRepository.findAll();
    }

    public Optional<Paquete> obtenerPorId(Long id) {
    return paqueteRepository.findById(id);
}

    public void guardar(Paquete paquete) {
        paqueteRepository.save(paquete);
    }

    public void eliminar(Long id) {
        paqueteRepository.deleteById(id);
    }
}