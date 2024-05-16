package com.threads.threads;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
public class Controller {

    public static final String PROCESS = "/process";

    private final RaceService raceService;

    @PostMapping(PROCESS)
    public ResponseEntity<Object> processInput(@RequestBody Input input) throws InterruptedException {

        return ResponseEntity.ok(raceService.winners(input.getInput()));
    }

}
