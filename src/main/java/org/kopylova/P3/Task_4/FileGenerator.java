package org.kopylova.P3.Task_4;

import io.reactivex.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FileGenerator {
    private final Random random = new Random();

    public Observable<File> generateFiles() {
        String[] types = {"XML", "JSON", "XLS"};
        return Observable.interval(random.nextInt(900) + 100, TimeUnit.MILLISECONDS)
                .map(tick -> new File(types[random.nextInt(types.length)], random.nextInt(91) + 10));
    }
}

