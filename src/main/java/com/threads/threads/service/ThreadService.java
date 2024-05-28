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
    private PrintService printService;

    public ThreadService(Horse horse, AtomicBoolean finishedRace, List<Horse> winners, Area area) {
        this.horse = horse;
        this.finishedRace = finishedRace;
        this.winners = winners;
        this.area = area;
        this.printService = new PrintService(winners);
    }


    @Override
    public void run() {

        while (!finishedRace.get() && !horse.reachGoal() && winners.size() < 3) {

            horse.setDistance(horse.getDistance() + getMetersToGo());

            if (area.isInArea(horse.getDistance())) {
                area.lock();
                try {
                    if (area.isInArea(horse.getDistance())) {
                        System.out.println(horse.getName() + " esta en el area y espera 7 segundos.");
                        Thread.sleep(7000);
                        horse.setDistance(horse.getDistance() + 100);
                        System.out.println(horse.getName() + " ha avanzado 100 metros despues de esperar, nueva posicion: " + horse.getDistance());
                    }
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                } finally {
                    area.unlock();
                }
            }

            printService.printIfCondition(horse);

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
        return horse.getSpeed() * (random.nextInt(10) + 1);
    }
}
