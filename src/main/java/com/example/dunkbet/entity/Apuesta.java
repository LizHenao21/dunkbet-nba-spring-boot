package com.example.dunkbet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una apuesta realizada en la plataforma DunkBet.
 * Esta clase mapea la tabla "apuesta" en la base de datos y define los atributos clave de un ticket 
 * de juego, tales como el usuario que apuesta, el partido involucrado, el tipo de apuesta (ganador o puntos), 
 * la predicción, el monto invertido, el estado (pendiente, ganada o perdida), las ganancias calculadas 
 * y la marca de tiempo del registro.
 * * @author Lizeth Henao
 */


@Entity
@Table(name = "apuesta")
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApuesta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_partido", nullable = false)
    private Partido partido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoApuesta tipo;

    @Column(length = 100, nullable = false)
    private String prediccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoApuesta estado;

    @Column(precision = 10, scale = 2)
    private BigDecimal ganancia;

    @Column(nullable = false)
    private LocalDateTime fechaApuesta;

    public Apuesta(Long idApuesta, Usuario usuario, Partido partido, TipoApuesta tipo, String prediccion,
            BigDecimal monto, EstadoApuesta estado, BigDecimal ganancia, LocalDateTime fechaApuesta) {
        this.idApuesta = idApuesta;
        this.usuario = usuario;
        this.partido = partido;
        this.tipo = tipo;
        this.prediccion = prediccion;
        this.monto = monto;
        this.estado = estado;
        this.ganancia = ganancia;
        this.fechaApuesta = fechaApuesta;
    }

    public enum TipoApuesta {
        GANADOR, PUNTOS
    }

    public enum EstadoApuesta {
        pendiente, ganada, perdida
    }


    public Apuesta() {
        this.ganancia = BigDecimal.ZERO;
        this.fechaApuesta = LocalDateTime.now();
        this.estado = EstadoApuesta.pendiente;
    }

    public Apuesta(Usuario usuario, Partido partido, TipoApuesta tipo, String prediccion, BigDecimal monto) {
        this();
        this.usuario = usuario;
        this.partido = partido;
        this.tipo = tipo;
        this.prediccion = prediccion;
        this.monto = monto;
    }

    public Long getIdApuesta() {
        return idApuesta;
    }

    public void setIdApuesta(Long idApuesta) {
        this.idApuesta = idApuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public TipoApuesta getTipo() {
        return tipo;
    }

    public void setTipo(TipoApuesta tipo) {
        this.tipo = tipo;
    }

    public String getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(String prediccion) {
        this.prediccion = prediccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public EstadoApuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoApuesta estado) {
        this.estado = estado;
    }

    public BigDecimal getGanancia() {
        return ganancia;
    }

    public void setGanancia(BigDecimal ganancia) {
        this.ganancia = ganancia;
    }

    public LocalDateTime getFechaApuesta() {
        return fechaApuesta;
    }

    public void setFechaApuesta(LocalDateTime fechaApuesta) {
        this.fechaApuesta = fechaApuesta;
    }
}