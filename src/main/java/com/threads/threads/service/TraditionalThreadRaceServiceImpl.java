package com.threads.threads.service;

import com.threads.threads.models.Area;
import com.threads.threads.models.Horse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Service
public class TraditionalThreadRaceServiceImpl implements TraditionalThreadRaceService {

    private final AtomicBoolean finishedRace = new AtomicBoolean(false);
    private final List<Horse> winners = new ArrayList<>();

    public List<Horse> winners(int horses) throws InterruptedException {

        finishedRace.set(false);
        winners.clear();

        //****************************** creacion **********************************************

        Area area = new Area();

        Thread areaUpdaterThread = new Thread(new AreaUpdaterService(area));
        areaUpdaterThread.start();

        List<Thread> threads = IntStream.range(0, horses)
                .mapToObj(i -> new Thread(new ThreadService(new Horse("Caballo " + i + 1), finishedRace, winners, area)))
                .toList();

        threads.forEach(Thread::start);

        //****************************** mientras no ganen 3 **********************************************
        while (winners.size() != 3 && !finishedRace.get()) {
            try {
                Thread.sleep(1000);
                if (winners.size() == 3) {
                    //interrumpe los hilos
                    threads.forEach(Thread::interrupt);
                    finishedRace.set(true);
                    areaUpdaterThread.interrupt();

                }
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        //****************************** imprimir ganadores **********************************************
        this.printWinners(winners);

        return winners;
    }


    private void printWinners(List<Horse> winners) throws InterruptedException {

        finishedRace.set(true);
        System.out.println("Caballos ganadores:");
        System.out.println("=========================================================================");
        int puesto = 1;
        for (Horse winner : winners) {
            Thread.sleep(1000);
            System.out.println(String.format("++++++++++++++++++++++++++++++++ Puesto %d++++++++++++++++++++++++++++++++++++++++", puesto));
            System.out.println(winner.getName());
            System.out.println(winner.getDistance());
            puesto++;
            Thread.sleep(1000);
        }
        finishedRace.set(false);
        System.out.println("=========================================================================");
    }

}

