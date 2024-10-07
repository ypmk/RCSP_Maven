package org.kopylova.P3.Task_2.T1;

import io.reactivex.Observable;
import java.util.Random;

//Преобразовать поток из случайного количества (от 0 до 1000)
//случайных чисел в поток, содержащий количество чисел.

public class Main {
    public static void main(String[] args) {
        System.out.println("Kopylova");
        Random random = new Random();
        int randomCount = random.nextInt(1001);

        Observable<Integer> randomNumbersObservable = Observable
                .range(0, randomCount)
                .map(i -> random.nextInt(1000));

        randomNumbersObservable
                .count()
                .subscribe(count -> System.out.println("Количество чисел в потоке: " + count),
                        Throwable::printStackTrace);

    }
}

