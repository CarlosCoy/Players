package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream()
                .filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        result.forEach((key, jugadores) -> {
            System.out.println("\n");
            System.out.println(key + ": ");
            jugadores.forEach(System.out::println);
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().filter(player -> player.getNational().equals("France"))
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println("El mejor jugador frances es "+player.getName()));
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().collect(Collectors.groupingBy(player -> player.getNational()))
                .forEach((pais, players) -> {
                    System.out.println("Pais: "+pais);
                    System.out.println("Clubs: ");
                    players.forEach(player -> System.out.println(player.getClub()));
                });
    }

    @Test
    void clubConElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println("El club con el mejor jugador es "+player.getClub()));

    }

    @Test
    void ElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println("El mejor jugador es "+player.getName()));

    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().collect(Collectors.groupingBy(player -> player.getNational()))
                .forEach((pais, players) -> {
                    players.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                            .ifPresent(player -> System.out.println("El mejor jugador de "+player.getNational()+ " es "+player.getName()));
                });
    }


}
