package org.kopylova.P3.Task_2.T2;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.Random;

//Даны два потока по 1000 элементов. Каждый содержит случайные цифры.
// Сформировать поток, обрабатывающий оба потока параллельно.
// Например, при входных потоках (1, 2, 3) и (4, 5, 6)
// выходной поток — (1, 4, 2, 5, 3, 6).

public class Main {
    public static void main(String[] args) {

        Random random = new Random();

        Observable<Integer> stream1 = Observable
                .range(0, 1000)
                .map(i -> random.nextInt(10))
                .subscribeOn(Schedulers.computation());

        Observable<Integer> stream2 = Observable
                .range(0, 1000)
                .map(i -> random.nextInt(10))
                .subscribeOn(Schedulers.computation());

        Observable.zip(stream1, stream2, (num1, num2) -> new Integer[]{num1, num2})
                .flatMap(pair -> Observable.fromArray(pair[0], pair[1]))
                .subscribe(
                        item -> System.out.print(item + " "),
                        Throwable::printStackTrace,
                        () -> System.out.println("\nKopylova \nПоток завершен")
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
