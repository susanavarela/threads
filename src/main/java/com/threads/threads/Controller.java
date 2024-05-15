package com.threads.threads;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

@RestController
public class Controller {
    private final AtomicBoolean carreraTerminada = new AtomicBoolean(false);
    private final List<Horse> ganadores = new ArrayList<>(); // Lista para almacenar los nombres de los caballos ganadores

    @PostMapping("/process")
    public void processInput(@RequestBody Input input) throws InterruptedException {

        //*************************** revisar que tipo de hilos *************************************

        // int availableProcessors = Runtime.getRuntime().availableProcessors();  tradicional o virtual

        //****************************** creacion **********************************************
        List<Thread> threads = IntStream.range(0, input.getInput())
                .mapToObj(i -> new Thread(new ThreadService(new Horse("Caballo " + i + 1, carreraTerminada), carreraTerminada, ganadores)))
                .toList();

        //****************************** inician **********************************************
        threads.forEach(Thread::start);

        //****************************** mientras no ganen 3 **********************************************
        while (ganadores.size() != 3 && !carreraTerminada.get()) {
            try {
                Thread.sleep(1000); // Esperar 1 segundo
                if (ganadores.size() == 3) {
                    //interrumpe los hilos
                    threads.forEach(Thread::interrupt);
                    carreraTerminada.set(true);

                }
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        //****************************** imprimir ganadores **********************************************
        this.imprimirGanadores(ganadores);

        //****************************** terminar flujos **********************************************

        ganadores.clear();
    }


    private void imprimirGanadores(List<Horse> ganadores) throws InterruptedException {

        carreraTerminada.set(true);
        System.out.println("Caballos ganadores:");
        System.out.println("=========================================================================");
        int puesto = 1;
        for (Horse ganador : ganadores) {
            Thread.sleep(1000);
            System.out.println(String.format("++++++++++++++++++++++++++++++++ Puesto %d++++++++++++++++++++++++++++++++++++++++", puesto));
            System.out.println(ganador.getName());
            puesto++;
            Thread.sleep(1000);
        }
        carreraTerminada.set(false);//limpia la carrera para volver a correr
        System.out.println("=========================================================================");


    }
}
