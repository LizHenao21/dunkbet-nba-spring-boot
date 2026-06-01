package com.example.dunkbet.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un usuario o apostador dentro de la plataforma DunkBet.
 * Esta clase mapea la tabla "usuario" en la base de datos, almacenando los datos personales y de 
 * contacto del cliente (nombre, tipo y número de documento, correo electrónico, edad y teléfono). 
 * Adicionalmente, establece una relación de uno a muchos (OneToMany) para agrupar y gestionar 
 * el historial de apuestas asociadas a cada cuenta.
 * * @author Lizeth Henao
 */


@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoIdentificacion tipoIdentificacion;

    @Column(unique = true, nullable = false)
    private Integer documento;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, length = 20)
    private String telefono;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apuesta> apuestas;

    public enum TipoIdentificacion {
        CC, CE
    }

    public Usuario() {}

    public Usuario(String nombre, TipoIdentificacion tipoIdentificacion, Integer documento, 
                   String email, Integer edad, String telefono) {
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.documento = documento;
        this.email = email;
        this.edad = edad;
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public Integer getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Apuesta> getApuestas() {
        return apuestas;
    }

    public void setApuestas(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }
    
}