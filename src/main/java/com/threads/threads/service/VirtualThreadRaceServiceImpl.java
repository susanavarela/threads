package com.threads.threads.service;

import com.threads.threads.models.Area;
import com.threads.threads.models.Horse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Service
public class VirtualThreadRaceServiceImpl implements VirtualThreadRaceService {

    private final AtomicBoolean finishedRace = new AtomicBoolean(false);
    private final List<Horse> winners = new ArrayList<>();
    private PrintService printService;

    public VirtualThreadRaceServiceImpl() {
        this.printService = new PrintService(winners);
    }

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
        finishedRace.set(true);
        printService.printWinners();
        finishedRace.set(false);

        executor.shutdown();

        return winners;
    }
}
