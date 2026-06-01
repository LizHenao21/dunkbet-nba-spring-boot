package com.example.dunkbet.service;

import com.example.dunkbet.entity.Usuario;
import com.example.dunkbet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la capa de servicio para la gestión de usuarios en DunkBet.
 * Esta clase contiene la lógica operativa para administrar las cuentas de los apostadores,
 * interactuando con el repositorio de usuarios de forma transaccional. Se encarga del guardado 
 * síncrono mediante persistencia inmediata (saveAndFlush), listados globales, búsquedas por ID,
 * y resuelve de manera robusta métodos sobrecargados de registro y búsqueda por documento, 
 * encargándose de la conversión e interpretación de formatos tanto en datos de tipo String como Integer.
 * * @author Lizeth Henao
 */


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {

        return usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorDocumento(Integer documento) {
        return usuarioRepository.findByDocumento(documento);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(String nombre, String tipoIdentificacion, Integer documento,
                                    String email, Integer edad, String telefono) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);

        usuario.setTipoIdentificacion(Usuario.TipoIdentificacion.valueOf(tipoIdentificacion.toLowerCase()));
        usuario.setDocumento(documento); 
        usuario.setEmail(email);
        usuario.setEdad(edad);
        usuario.setTelefono(telefono);
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorDocumento(String documento) {
        try {
            Integer docInt = Integer.parseInt(documento.trim());
            return this.buscarPorDocumento(docInt);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(String nombre, String tipoIdentificacion, String documento, String email,
                                    Integer edad, String telefono) {
        Integer docInt = Integer.parseInt(documento.trim());
        return this.registrarUsuario(nombre, tipoIdentificacion, docInt, email, edad, telefono);
    }
}