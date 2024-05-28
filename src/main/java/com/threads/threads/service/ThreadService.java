package com.threads.threads.service;

import com.threads.threads.models.Area;
import com.threads.threads.models.Horse;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadService implements Runnable {

    private final Horse horse;
    private final AtomicBoolean finishedRace;
    private final List<Horse> winners;
    private final Area area;

    public ThreadService(Horse horse, AtomicBoolean finishedRace, List<Horse> winners, Area area) {
        this.horse = horse;
        this.finishedRace = finishedRace;
        this.winners = winners;
        this.area = area;
    }

    @Override
    public void run() {

        // Comienza la carrera
        while (!finishedRace.get() && !horse.reachGoal() && winners.size() < 3) {

            // Avanza los metros
            horse.setDistance(horse.getDistance() + getMetersToGo());

            // Verifica si esta en el area
            if (area.isInArea(horse.getDistance())) {
                area.lock();
                try {
                    if (area.isInArea(horse.getDistance())) {
                        System.out.println(horse.getName() + " está en el área y espera 7 segundos.");
                        Thread.sleep(7000); // Espera 7 segundos
                        horse.setDistance(horse.getDistance() + 100); // Avanza 100 metros
                        System.out.println(horse.getName() + " ha avanzado 100 metros después de esperar, nueva posición: " + horse.getDistance());
                    }
                } catch (InterruptedException e) {
                   // e.printStackTrace();
                } finally {
                    area.unlock();
                }
            }
            // Imprimir el avance de cada caballo
            System.out.println(horse.getName() + " ha avanzado a " + horse.getDistance() + " metros.");

            // Verifica si ha alcanzado la meta
            if (horse.reachGoal() && winners.size() < 3) {
                winners.add(horse);
                System.out.println(horse.getName() + " ha alcanzado la meta!");
            }

            try {
                int sleep = horse.calculateWaitingTime() * 1000;
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
              //  e.printStackTrace();
            }
        }
    }

    private int getMetersToGo() {
        Random random = new Random();
        return horse.getSpeed() * (random.nextInt(10) + 1); // Entre 1 y 10 metros
    }
}
