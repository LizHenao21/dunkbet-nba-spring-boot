package com.example.dunkbet.controller;

import com.example.dunkbet.entity.Partido;
import com.example.dunkbet.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/partidos") 
public class PartidoController {

    @Autowired
    private PartidoService partidoService;


    @GetMapping
    public String listarPartidos(Model model) {
        model.addAttribute("partidos", partidoService.listarTodos());
        return "partidos/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("partido", new Partido());
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

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Partido partido = partidoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de partido inválido: " + id));
        
        model.addAttribute("partido", partido);
        return "partidos/formularioRegistro";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPartido(@PathVariable("id") Long id) {
        partidoService.eliminarPorId(id);
        return "redirect:/partidos";
    }
}