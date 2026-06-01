package com.example.dunkbet.controller;

import com.example.dunkbet.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal de la plataforma DunkBet.
 * Esta clase se encarga de gestionar el acceso a la página de inicio pública (home/index),
 * sirviendo como el punto de entrada principal para los usuarios y cargando la lista completa 
 * de partidos disponibles en el sistema para que puedan ser visualizados.
 * * @author Lizeth Henao
 */


@Controller
public class MainController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping({"/", "/index"})
    public String mostrarInicio(Model model) {
        model.addAttribute("partidos", partidoService.listarTodos());
        return "index";
    }
}