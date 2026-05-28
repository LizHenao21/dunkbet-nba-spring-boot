package com.example.dunkbet.controller;

import com.example.dunkbet.entity.Partido;
import com.example.dunkbet.repository.ApuestaRepository;
import com.example.dunkbet.service.PartidoService;
import com.example.dunkbet.service.ApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PartidoService partidoService;

    @Autowired
    private ApuestaService apuestaService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        if (username.equals("admin") && password.equals("1234")) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Credenciales incorrectas. Intente de nuevo.");
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("partidos", partidoService.listarTodos());
        return "admin/dashboard";
    }

    @GetMapping("/partidos/nuevo")
    public String formularioregistro() {
        return "partidos/formularioRegistro";
    }

    @PostMapping("/partidos/guardar")
    public String guardarPartido(@ModelAttribute Partido partido, Model model, RedirectAttributes redirectAttributes) {
    
        String local = partido.getEquipoLocal().trim();
        String visitante = partido.getEquipoVisitante().trim();

        if (local.equalsIgnoreCase(visitante)) {
            model.addAttribute("error", "Error: El equipo local y el equipo visitante no pueden ser el mismo.");
            return "partidos/formularioRegistro";
        }

        partido.setEstado(Partido.EstadoPartido.abierto);
        partidoService.guardar(partido);
    
        redirectAttributes.addFlashAttribute("exito", "Partido registrado exitosamente");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/partidos/cerrar")
    public String formularioCierre(@RequestParam("partidoId") Long partidoId, Model model) {
        Partido partido = partidoService.obtenerPorId(partidoId).orElse(null);
        model.addAttribute("partido", partido);
        return "apuestas/formularioCierre";
    }

    @PostMapping("/partidos/cerrar")
    public String cerrarPartido(
            @RequestParam("partidoId") Long partidoId,
            @RequestParam("puntosLocal") Integer puntosLocal,
            @RequestParam("puntosVisitante") Integer puntosVisitante,
            RedirectAttributes redirectAttributes) {

        Partido partido = partidoService.obtenerPorId(partidoId).orElse(null);
        
        if (partido != null) {
            try {
                apuestaService.liquidarApuestasPorPartido(partidoId, puntosLocal, puntosVisitante);

                partido.setPuntosLocal(puntosLocal);
                partido.setPuntosVisitante(puntosVisitante);
                partido.setEstado(Partido.EstadoPartido.cerrado);

                if (puntosLocal > puntosVisitante) {
                    partido.setEquipoGanador(partido.getEquipoLocal());
                } else if (puntosVisitante > puntosLocal) {
                    partido.setEquipoGanador(partido.getEquipoVisitante());
                } else {
                    partido.setEquipoGanador("Empate");
                }

                partidoService.guardar(partido);
                redirectAttributes.addFlashAttribute("exito", "Partido cerrado y apuestas liquidadas correctamente.");

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al liquidar las apuestas: " + e.getMessage());
            }
        }
        return "redirect:/admin/dashboard";
    }

    @Autowired
    private ApuestaRepository apuestaRepository;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        try {
            java.util.List<com.example.dunkbet.entity.Apuesta> apuestas = apuestaRepository.findAll();
        
            for (com.example.dunkbet.entity.Apuesta a : apuestas) {
                if (a.getEstado() == null) {
                }
            }
        
        model.addAttribute("apuestas", apuestas);
    } catch (Exception e) {
        model.addAttribute("error", "Ocurrió un error al cargar el listado: " + e.getMessage());
    }
    return "admin/usuarios";
}

    @PostMapping("/partidos/eliminar")
    public String eliminarPartido(@RequestParam("partidoId") Long partidoId, RedirectAttributes redirectAttributes) {
        try {
            Partido partido = partidoService.obtenerPorId(partidoId).orElse(null); 
        
            if (partido == null) {
                redirectAttributes.addFlashAttribute("error", "El partido que intentas eliminar no existe.");
                return "redirect:/admin/dashboard";
            }
        
            if (!partido.getEstado().name().equalsIgnoreCase("cerrado")) {
                redirectAttributes.addFlashAttribute("error", "No se puede eliminar: Solo se permite borrar partidos que ya estén en estado \"cerrado\"");
                return "redirect:/admin/dashboard";
}

            apuestaService.eliminarApuestasPorPartidoId(partidoId);
        
            partidoService.eliminarPorId(partidoId); 
        
            redirectAttributes.addFlashAttribute("exito", "El partido finalizado y todas sus apuestas asociadas se han eliminado correctamente.");
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error inesperado al intentar eliminar el partido: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}