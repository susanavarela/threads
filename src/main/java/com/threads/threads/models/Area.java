package com.threads.threads.models;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Area {
    private int min;
    private int max;
    private final Lock lock = new ReentrantLock();

    //Actualiza el area
    public void updateArea() {
        lock.lock(); // bloquea
        try {
            Random random = new Random();
            min = random.nextInt(951);
            max = min + 50;
            System.out.println("Area actualizada: min = " + min + ", max = " + max);
        } finally {
            lock.unlock(); // Libera el bloqueo
        }
    }

    // Verifica si una posición esta dentro del area
    public boolean isInArea(int position) {
        return position >= min && position <= max;
    }

    // Bloquea el area para un caballo
    public void lock() {
        lock.lock();
    }

    // Desbloquea el área para otros caballos
    public void unlock() {
        lock.unlock();
    }
}
