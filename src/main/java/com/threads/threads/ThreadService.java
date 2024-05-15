package com.threads.threads;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadService implements Runnable {

    private final Horse horse;
    private final AtomicBoolean carreraTerminada;
    private final List<Horse> ganadores; // Lista para almacenar los nombres de los caballos ganadores

    public ThreadService(Horse horse, AtomicBoolean carreraTerminada, List<Horse> ganadores) {
        this.horse = horse;
        this.carreraTerminada = carreraTerminada;
        this.ganadores = ganadores;
    }

    @Override
    public void run() {
        // Comienza la carrera
        while (!carreraTerminada.get() && !horse.haAlcanzadoMeta() && ganadores.size() != 3) {

            //suma los metros
            this.getMetrosARecorrer();


            // Si supera la meta, ajustar los metros recorridos y marcar la carrera como terminada
            if (horse.getDistance() >= 100) {//*************************************************************************volver a poner 1000
                horse.setDistance(1000);//si supera los 1000 marcar como 1000 para mostrarlo
                System.out.println(horse.getName() + " ha alcanzado la meta!***************************************************************");
            } else {
                System.out.println(horse.getName() + " ha avanzado " + horse.getDistance() + " metros.");
            }


            // Si el caballo aún no ha alcanzado la meta
            try {
                int sleep = (int) horse.calcularTiempoEspera() * 1000;
                Thread.sleep(sleep); // Convertir segundos a milisegundos
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        if (horse.haAlcanzadoMeta() && ganadores.size() != 3) {
            ganadores.add(horse);

        }

    }

    private void getMetrosARecorrer() {
        // Calcular los metros a avanzar, considerando la velocidad
        Random random = new Random();
        int metrosARecorrer = horse.getSpeed() * (random.nextInt(10) + 1); // Entre 1 y 10 metros
        horse.setDistance(horse.getDistance() + metrosARecorrer);
    }


}