package com.threads.threads;

import java.util.List;

public interface RaceService {

    List<Horse> winners(int horses) throws InterruptedException;
}
