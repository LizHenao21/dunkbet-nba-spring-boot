package com.example.dunkbet.controller;

import com.example.dunkbet.entity.Partido;
import com.example.dunkbet.service.ApuestaService;
import com.example.dunkbet.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/apuestas")
public class ApuestaController {

    @Autowired
    private ApuestaService apuestaService;

    @Autowired
    private PartidoService partidoService;
    
    @GetMapping("/nueva")
    public String mostrarFormularioApuesta(@RequestParam("partidoId") Long partidoId, Model model) {
        model.addAttribute("partido", partidoService.obtenerPorId(partidoId).orElse(null));
        model.addAttribute("tipoApuesta", "GANADOR");
        model.addAttribute("monto", BigDecimal.ZERO);
        model.addAttribute("ganancia", BigDecimal.ZERO);
        return "apuestas/formularioApuesta";
    }

    @PostMapping(value = "/nueva")
    public String actualizarFormulario(
            @RequestParam("partidoId") Long partidoId,
            @RequestParam("tipoApuesta") String tipoApuesta,
            @RequestParam(value = "monto", required = false) BigDecimal monto,
            @RequestParam(value = "prediccionEquipo", required = false) String prediccionEquipo, // <-- AGREGADO
            @RequestParam(value = "prediccionPuntos", required = false) String prediccionPuntos, // <-- AGREGADO
            Model model) {
        
        Partido partido = partidoService.obtenerPorId(partidoId).orElse(null);
        model.addAttribute("partido", partido);
        model.addAttribute("tipoApuesta", tipoApuesta);
        
        if (monto == null) {
            monto = BigDecimal.ZERO;
        }
        model.addAttribute("monto", monto);

        String prediccionFinal = "GANADOR".equals(tipoApuesta) ? prediccionEquipo : prediccionPuntos;
        
        model.addAttribute("prediccion", prediccionFinal);

        BigDecimal ganancia = apuestaService.calcularGananciaPotencial(monto, tipoApuesta);
        model.addAttribute("ganancia", ganancia);

        return "apuestas/formularioApuesta";
    }

    @PostMapping(value = "/guardar")
    public String guardarApuesta(
            @RequestParam("nombre") String nombre,
            @RequestParam("tipoIdentificacion") String tipoIdentificacion,
            @RequestParam("documento") String documento,
            @RequestParam("email") String email,
            @RequestParam("edad") Integer edad,
            @RequestParam("telefono") String telefono,
            @RequestParam("partidoId") Long partidoId,
            @RequestParam("tipoApuesta") String tipoApuesta,
            @RequestParam(value = "prediccionEquipo", required = false) String prediccionEquipo,
            @RequestParam(value = "prediccionPuntos", required = false) String prediccionPuntos,
            @RequestParam("monto") BigDecimal monto,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        String prediccionFinal = "GANADOR".equals(tipoApuesta) ? prediccionEquipo : prediccionPuntos;
        
        try {
            if (prediccionFinal == null || prediccionFinal.trim().isEmpty()) {
                throw new IllegalArgumentException("Debe ingresar su predicción (seleccionar equipo o ingresar puntos).");
            }

            apuestaService.registrarApuesta(nombre, tipoIdentificacion, Integer.parseInt(documento.trim()), email, 
                                            edad, telefono, partidoId, tipoApuesta, prediccionFinal, monto);
            
            redirectAttributes.addFlashAttribute("exito", "¡Apuesta registrada con éxito! Puedes consultar tu historial abajo.");
            return "redirect:/index";
            
        } catch (NumberFormatException nfe) {
            model.addAttribute("error", "El número de documento supera los límites del sistema numérico.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("partido", partidoService.obtenerPorId(partidoId).orElse(null));
        model.addAttribute("tipoApuesta", tipoApuesta);
        model.addAttribute("monto", monto);
        model.addAttribute("prediccion", prediccionFinal);
        model.addAttribute("ganancia", apuestaService.calcularGananciaPotencial(monto, tipoApuesta));
        return "apuestas/formularioApuesta";
    }

    @GetMapping("/historial")
    public String verHistorial(@RequestParam(value = "documento", required = false) String documento, Model model) {
        if (documento != null && !documento.trim().isEmpty()) { 
            try {
                Integer docInteger = Integer.parseInt(documento.trim());
                model.addAttribute("apuestas", apuestaService.obtenerHistorialPorDocumento(docInteger));
                model.addAttribute("documentoBuscado", documento);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "El número de documento ingresado no es válido.");
            } catch (Exception e) {
                model.addAttribute("error", "No se encontró historial para ese documento.");
            }
        }
        return "apuestas/historial";
    }

    @PostMapping("/liquidar")
    public String liquidarPartido(
            @RequestParam("partidoId") Long partidoId,
            @RequestParam("puntosLocal") int puntosLocal,
            @RequestParam("puntosVisitante") int puntosVisitante,
            Model model) {
        try {
            apuestaService.liquidarApuestasPorPartido(partidoId, puntosLocal, puntosVisitante);
            return "redirect:/index?exito=Partido+liquidado+con+exito";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/index?error=" + e.getMessage();
        }
    }
}