package com.threads.threads.controller;

import com.threads.threads.models.Horse;
import com.threads.threads.models.Input;
import com.threads.threads.service.TraditionalThreadRaceService;
import com.threads.threads.service.VirtualThreadRaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
public class Controller {

    public static final String PROCESS = "/process";

    private final TraditionalThreadRaceService traditionalService;
    private final VirtualThreadRaceService virtualService;

    @PostMapping(PROCESS)
    public ResponseEntity<Object> processInput(@RequestBody Input input) throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        List<Horse> horseList = new ArrayList<>();

        if(availableProcessors >= input.getInput()){
            horseList = traditionalService.winners(input.getInput());
        }else{
            horseList = virtualService.winners(input.getInput());
        }
        return ResponseEntity.ok(horseList);
    }

}
