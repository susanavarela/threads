package com.threads.threads;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@Service
public class RaceServiceImpl implements RaceService{

    private final AtomicBoolean finishedRace = new AtomicBoolean(false);
    private final List<Horse> winners = new ArrayList<>(); // Lista para almacenar los nombres de los caballos ganadores

    public List<Horse> winners(int horses) throws InterruptedException {


        //*************************** revisar que tipo de hilos *************************************

        // int availableProcessors = Runtime.getRuntime().availableProcessors();  tradicional o virtual

        //****************************** creacion **********************************************
        List<Thread> threads = IntStream.range(0, horses)
                .mapToObj(i -> new Thread(new ThreadService(new Horse("Caballo " + i + 1), finishedRace, winners)))
                .toList();

        //****************************** inician **********************************************
        threads.forEach(Thread::start);

        //****************************** mientras no ganen 3 **********************************************
        while (winners.size() != 3 && !finishedRace.get()) {
            try {
                Thread.sleep(1000); // Esperar 1 segundo
                if (winners.size() == 3) {
                    //interrumpe los hilos
                    threads.forEach(Thread::interrupt);
                    finishedRace.set(true);

                }
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        //****************************** imprimir ganadores **********************************************
        this.printWinners(winners);

        //****************************** terminar flujos **********************************************
        winners.clear();
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
        finishedRace.set(false);//limpia la carrera para volver a correr
        System.out.println("=========================================================================");
    }

}

