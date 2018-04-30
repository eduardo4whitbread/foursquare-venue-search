package com.eduardosantos.foursquareexercise.base;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class BaseTestScheduler extends BaseScheduler {
    @Inject
    public BaseTestScheduler() {
    }

    @Override
    public Worker createWorker() {
        return Schedulers.trampoline().createWorker();
    }
}
