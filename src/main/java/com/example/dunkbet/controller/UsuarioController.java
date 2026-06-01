package com.example.dunkbet.controller;

import com.example.dunkbet.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador encargado de la gestión y visualización de los usuarios en la plataforma DunkBet.
 * Esta clase maneja las peticiones relacionadas con los clientes del sistema, permitiendo 
 * principalmente listar y exponer su información general en la vista correspondiente del panel.
 * * @author Lizeth Henao
 */

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/listar";
    }
}