package com.threads.threads;

public class AreaUpdater implements Runnable {
    private final Area area;

    public AreaUpdater(Area area) {
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
