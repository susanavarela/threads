package com.threads.threads.service;

import com.threads.threads.models.Horse;

import java.util.List;

public interface RaceService {

    List<Horse> winners(int horses) throws InterruptedException;
}
