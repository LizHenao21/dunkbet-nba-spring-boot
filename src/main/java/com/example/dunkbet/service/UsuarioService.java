package com.example.dunkbet.service;

import com.example.dunkbet.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> obtenerPorId(Long id);
    Optional<Usuario> buscarPorDocumento(String documento);
    List<Usuario> listarTodos();
    Usuario registrarUsuario(String nombre, String tipoIdentificacion, String documento,
                            String email, Integer edad, String telefono);
    Optional<Usuario> buscarPorDocumento(Integer documento);
    Usuario registrarUsuario(String nombre, String tipoIdentificacion, Integer documento, String email, Integer edad,
            String telefono);
}