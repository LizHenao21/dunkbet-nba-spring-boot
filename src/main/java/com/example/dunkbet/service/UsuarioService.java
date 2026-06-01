package com.example.dunkbet.service;

import com.example.dunkbet.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio que define la lógica de negocio para la gestión de usuarios en DunkBet.
 * Establece el contrato para los métodos de administración de clientes, incluyendo el almacenamiento, 
 * la consulta de perfiles por identificador único o documento de identidad (soportando formatos de 
 * cadena y enteros), el listado completo de los apostadores registrados y los métodos sobrecargados 
 * para el registro seguro de nuevos usuarios en el sistema.
 * * @author Lizeth Henao
 */

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