package com.threads.threads.service;

import com.threads.threads.models.Area;

public class AreaUpdaterService implements Runnable {
    private final Area area;

    public AreaUpdaterService(Area area) {
        this.area = area;
    }

    @Override
    public void run() {
        while (true) {
            area.updateArea();
            try {
                Thread.sleep(15000);
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
