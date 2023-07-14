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
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable.filter(player -> player.getNational().equals("France"))
                .collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(a -> a.getWinners() / a.getGames()))
                        .get()).subscribe(player -> System.out.println("Reactivo: el mejor jugador frances es "+player.getName()));



    }

    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);
        observable
                .groupBy(player -> player.getNational())
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(tempList -> {
                            List<String> clubs = new ArrayList<>();
                            tempList.forEach(element -> clubs.add(element.getClub()));
                            Map<String, List<String>> map = new HashMap<>();
                            map.put(groupedFlux.key(), clubs);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((pais, value) -> {
                        System.out.println("\n");
                        System.out.println("Reactivo: clubes de " +pais + ": ");
                        value.forEach(System.out::println);
                    });
                });

    }

    @Test
    void clubConElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(CsvUtilFile.getPlayers());
        observable.collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(i -> i.getWinners() / i.getGames()))
                        .get()).subscribe(player -> System.out.println("Reactivo: El club con el mejor jugador es "+player.getClub()));
    }


    @Test
    void ElMejorJugador() {
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(CsvUtilFile.getPlayers());
        observable.collectList()
                .map(playerList -> playerList.stream().max(Comparator.comparingInt(i -> i.getWinners() / i.getGames()))
                        .get()).subscribe(player -> System.out.println("Reactivo: El mejor jugador es "+player.getName()));
    }


    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable
                .groupBy(Player::getNational)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> {
                            Player best = list.stream().reduce((p1, p2)->((p1.getWinners()/p1.getGames())>=(p2.getWinners()/p2.getGames())?p1:p2)).get();
                            Map<String, Player> map = new HashMap<>();
                            map.put(groupedFlux.key(), best);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((pais, player) -> {
                        System.out.println("\n");
                        System.out.println("Reactivo: el mejor jugador de "+pais + " es "+player.getName());
                    });
                });


    }



}
