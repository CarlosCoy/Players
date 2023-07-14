package com.example.demo;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class DojoReactiveTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35() {
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable.filter(jugador -> jugador.getAge() > 35)
                .subscribe(System.out::println);
    }


    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable.filter(player -> player.getAge() > 35)
                .distinct()
                .groupBy(Player::getClub)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> {
                            Map<String, List<Player>> map = new HashMap<>();
                            map.put(groupedFlux.key(), list);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((key, value) -> {
                        System.out.println("\n");
                        System.out.println(key + ": ");
                        value.forEach(System.out::println);
                    });
                });

    }


    @Test
    void mejorJugadorConNacionalidadFrancia(){

    }

    @Test
    void clubsAgrupadosPorNacionalidad(){

    }

    @Test
    void clubConElMejorJugador(){
    }

    @Test
    void clubConElMejorJugador2() {
    }

    @Test
    void ElMejorJugador() {
    }

    @Test
    void ElMejorJugador2() {
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
    }



}
