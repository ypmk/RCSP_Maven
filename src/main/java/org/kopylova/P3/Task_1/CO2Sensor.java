package org.kopylova.P3.Task_1;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import java.util.Random;

// Класс для датчика CO2
class CO2Sensor extends Observable<Integer> {
    private final PublishSubject<Integer> subject = PublishSubject.create();
    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        subject.subscribe(observer);
    }
    public void start() {
        new Thread(() -> {
            while (true) {
                int co2 = new Random().nextInt(30, 101);
                subject.onNext(co2);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

