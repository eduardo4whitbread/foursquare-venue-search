package com.eduardosantos.foursquareexercise.base;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BaseScheduler extends Scheduler {
    @Inject
    public BaseScheduler() {
        super();
    }

    @Override
    public Worker createWorker() {
        return Schedulers.io().createWorker();
    }
}
