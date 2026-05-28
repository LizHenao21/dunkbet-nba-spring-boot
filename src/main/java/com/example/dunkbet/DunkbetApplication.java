package com.example.dunkbet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dunkbet.entity.Usuario;
import com.example.dunkbet.entity.Partido;
import com.example.dunkbet.repository.UsuarioRepository;
import com.example.dunkbet.repository.PartidoRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class DunkbetApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(DunkbetApplication.class, args);
    }
	 
    @Override
    public void run(String... args) throws Exception {
        /*
        if (usuarioRepository.count() == 0 && partidoRepository.count() == 0) {
            
            Usuario usuario1 = new Usuario("Lizeth", Usuario.TipoIdentificacion.CC, 12345678, "prueba1@gmail.com", 18, "1234567");
            usuarioRepository.save(usuario1);
            
            Usuario usuario2 = new Usuario("Angie", Usuario.TipoIdentificacion.CC, 87654321, "prueba2@gmail.com", 18, "7654321");
            usuarioRepository.save(usuario2);
            
            Usuario usuario3 = new Usuario("Lizeth", Usuario.TipoIdentificacion.CC, 456123789, "prueba3@gmail.com", 18, "7651234");
            usuarioRepository.save(usuario3);
            
            System.out.println("Usuarios de prueba insertados correctamente");

            Partido partido1 = new Partido();
            partido1.setEquipoLocal("Boston Celtics");
            partido1.setEquipoVisitante("New York Knicks");
            partido1.setFecha(LocalDate.parse("2026-06-02")); 
            partido1.setHora(LocalTime.parse("18:00"));       
            partido1.setLugar("TD Garden");
            partido1.setEstado(Partido.EstadoPartido.abierto); 
            partidoRepository.save(partido1);

            Partido partido2 = new Partido();
            partido2.setEquipoLocal("Los Angeles Lakers");
            partido2.setEquipoVisitante("Golden State Warriors");
            partido2.setFecha(LocalDate.parse("2026-06-03"));
            partido2.setHora(LocalTime.parse("20:30"));
            partido2.setLugar("Crypto.com Arena");
            partido2.setEstado(Partido.EstadoPartido.abierto);
            partidoRepository.save(partido2);

            Partido partido3 = new Partido();
            partido3.setEquipoLocal("Miami Heat");
            partido3.setEquipoVisitante("Milwaukee Bucks");
            partido3.setFecha(LocalDate.parse("2026-06-04"));
            partido3.setHora(LocalTime.parse("19:15"));
            partido3.setLugar("Kaseya Center");
            partido3.setEstado(Partido.EstadoPartido.abierto);
            partidoRepository.save(partido3);

            System.out.println("3 Partidos próximos insertados correctamente");
            
        } else {
            System.out.println("Los datos de prueba ya existen en la base de datos");
        }
		*/
    }
		
}