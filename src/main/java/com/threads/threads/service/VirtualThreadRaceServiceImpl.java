package com.threads.threads.service;

import com.threads.threads.models.Area;
import com.threads.threads.models.Horse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.concurrent.Executors;
@Service
public class VirtualThreadRaceServiceImpl implements VirtualThreadRaceService {

    private final AtomicBoolean finishedRace = new AtomicBoolean(false);
    private final List<Horse> winners = new ArrayList<>();

    public List<Horse> winners(int horses) throws InterruptedException {

        finishedRace.set(false);
        winners.clear();

        //****************************** creacion **********************************************

        Area area = new Area();

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        Future<?> areaUpdaterFuture = executor.submit(new AreaUpdaterService(area));

        List<? extends Future<?>> futures = IntStream.range(0, horses)
                .mapToObj(i -> executor.submit(new ThreadService(new Horse("Caballo " + (i + 1)), finishedRace, winners, area)))
                .toList();

        //****************************** mientras no ganen 3 **********************************************
        while (winners.size() != 3 && !finishedRace.get()) {
            try {
                Thread.sleep(1000);
                if (winners.size() == 3) {
                    // Cancelar las tareas
                    futures.forEach(future -> future.cancel(true));
                    finishedRace.set(true);
                    areaUpdaterFuture.cancel(true);
                }
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (CancellationException | ExecutionException e) {
                // e.printStackTrace();
            }
        }

        //****************************** imprimir ganadores **********************************************
        this.printWinners(winners);

        executor.shutdown();

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
