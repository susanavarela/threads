package com.threads.threads.service;

import com.threads.threads.models.Horse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class PrintService{

    private final Lock lock = new ReentrantLock();
    private final List<Horse> winners;

    public PrintService(List<Horse> winners) {
        this.winners = winners;
    }

    public void printIfCondition(Horse horse) {
        lock.lock();
        try {
            if (winners.size() < 3) {
                System.out.println(horse.getName() + " ha avanzado a " + horse.getDistance() + " metros.");
            }
        } finally {
            lock.unlock();
        }
    }

    public void printWinners() throws InterruptedException {

        System.out.println("===========================Caballos ganadores========================================");
        int puesto = 1;
        for (Horse winner : winners) {
            Thread.sleep(1000);
            System.out.println(String.format("++++++++++++++++++++++++++++++++ Puesto %d++++++++++++++++++++++++++++++++++++++++", puesto));
            System.out.println(winner.getName());
            System.out.println(winner.getDistance());
            puesto++;
            Thread.sleep(1000);
        }

        System.out.println("=========================================================================");
    }
}
