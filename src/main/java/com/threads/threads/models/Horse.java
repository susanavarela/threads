package com.threads.threads.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@AllArgsConstructor
@Getter
@Setter
public class Horse {
    private int speed;
    private int resiliency;
    private String name;
    private int distance;

    public Horse(String name) {
        this.name = name;
        this.speed = generateRandomValueBetween(1, 3);
        this.resiliency = generateRandomValueBetween(1, 3);
    }

    private int generateRandomValueBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public boolean reachGoal() {
        return distance >= 100;
    }

    public int calculateWaitingTime() {
        int sleep = generateRandomValueBetween(1, 5);
        return Math.min(sleep, resiliency);
    }
}
