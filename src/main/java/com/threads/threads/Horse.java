package com.threads.threads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
@Getter
@Setter
public class Horse {
    private int speed;
    private int resiliency;
    private String name;
    private int distance;

    public Horse(String name, AtomicBoolean carreraTerminada) {
        this.name = name;
        this.speed = generarValorAleatorioEntre(1, 3);
        this.resiliency = generarValorAleatorioEntre(1, 3);
    }

    private int generarValorAleatorioEntre(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public boolean haAlcanzadoMeta() {
        return distance >= 100;
    }

    public int calcularTiempoEspera() {
        int tiempoEspera = generarValorAleatorioEntre(1, 5); // Entre 1 y 5 segundos
        return Math.min(tiempoEspera, resiliency);
    }

//    public void avanzar() {
//        if (!carreraTerminada.get() && !haAlcanzadoMeta()) {
//            // Calcular los metros a avanzar, considerando la velocidad
//            Random random = new Random();
//            int metrosARecorrer = speed * (random.nextInt(10) + 1); // Entre 1 y 10 metros
//
//            // Simular el avance sumando los metros recorridos
//            distance += metrosARecorrer;
//
//            // Si supera la meta, ajustar los metros recorridos y marcar la carrera como terminada
//            if (haAlcanzadoMeta()) {
//                distance = 1000;
//                carreraTerminada.set(true);
//                System.out.println(name + " ha alcanzado la meta!");
//            }
//        }
//    }
}
