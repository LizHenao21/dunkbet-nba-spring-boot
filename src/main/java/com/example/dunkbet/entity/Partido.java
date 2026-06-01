package com.example.dunkbet.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un partido de baloncesto dentro de la plataforma DunkBet.
 * Esta clase mapea la tabla "partido" en la base de datos, almacenando la información 
 * crucial de cada encuentro: los equipos participante (local y visitante), la fecha, 
 * la hora, el escenario deportivo, el estado actual del juego (abierto o cerrado), 
 * el marcador final y el equipo que resultó ganador del compromiso.
 * * @author Lizeth Henao
 */

@Entity
@Table(name = "partido")
public class Partido {

    public Partido(Long idPartido, String equipoLocal, String equipoVisitante, LocalDate fecha, LocalTime hora,
            String lugar, EstadoPartido estado, Integer puntosLocal, Integer puntosVisitante, String equipoGanador) {
        this.idPartido = idPartido;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
        this.estado = estado;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.equipoGanador = equipoGanador;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartido;

    @Column(name = "equipo_local", nullable = false, length = 100)
    private String equipoLocal;

    @Column(name = "equipo_visitante", nullable = false, length = 100)
    private String equipoVisitante;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false, length = 100)
    private String lugar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPartido estado;

    @Column(name = "puntos_local")
    private Integer puntosLocal = 0;

    @Column(name = "puntos_visitante")
    private Integer puntosVisitante = 0;

    @Column(name = "equipo_ganador", length = 100)
    private String equipoGanador;

    public enum EstadoPartido {
        abierto, cerrado
    }

    public Partido() {
        this.estado = EstadoPartido.abierto;
        this.puntosLocal = 0;
        this.puntosVisitante = 0;
    }

    public Partido(String equipoLocal, String equipoVisitante, LocalDate fecha, LocalTime hora, String lugar) {
        this();
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }

    public Integer getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(Integer puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public Integer getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(Integer puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public String getEquipoGanador() {
        return equipoGanador;
    }

    public void setEquipoGanador(String equipoGanador) {
        this.equipoGanador = equipoGanador;
    }
}