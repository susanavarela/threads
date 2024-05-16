package com.threads.threads;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadService implements Runnable {

    private final Horse horse;
    private final AtomicBoolean finishedRace;
    private final List<Horse> winners; // Lista para almacenar los nombres de los caballos ganadores

    public ThreadService(Horse horse, AtomicBoolean finishedRace, List<Horse> winners) {
        this.horse = horse;
        this.finishedRace = finishedRace;
        this.winners = winners;
    }

    @Override
    public void run() {
        // Comienza la carrera
        while (!finishedRace.get() && !horse.reachGoal() && winners.size() != 3) {

            //suma los metros
            this.getMetersToGo();


            // Si supera la meta, ajustar los metros recorridos y marcar la carrera como terminada
            if (horse.getDistance() >= 100) {//*************************************************************************volver a poner 1000
                System.out.println(horse.getName() + " ha alcanzado la meta!***************************************************************");
            } else {
                System.out.println(horse.getName() + " ha avanzado " + horse.getDistance() + " metros.");
            }


            // Si el caballo aún no ha alcanzado la meta
            try {
                int sleep = (int) horse.calculateWaitingTime() * 1000;
                Thread.sleep(sleep); // Convertir segundos a milisegundos
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        if (horse.reachGoal() && winners.size() != 3) {
            winners.add(horse);
        }
    }

    private void getMetersToGo() {
        // Calcular los metros a avanzar, considerando la velocidad
        Random random = new Random();
        int metersToGo = horse.getSpeed() * (random.nextInt(10) + 1); // Entre 1 y 10 metros
        horse.setDistance(horse.getDistance() + metersToGo);
    }


}