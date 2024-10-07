package org.kopylova.P3.Task_2.T3;

import io.reactivex.Observable;
import java.util.Random;

//Дан поток из случайного количества случайных чисел.
// Сформировать поток, содержащий только последнее число.

public class Main {
    public static void main(String[] args) {

        Random random = new Random();

        int randomCount = random.nextInt(1001);

        Observable<Integer> randomNumbersObservable = Observable
                .range(0, randomCount)
                .map(i -> random.nextInt(1000));

        randomNumbersObservable
                .lastElement()
                .subscribe(lastNumber -> System.out.println("\nKopylova \nПоследнее число в потоке: " + lastNumber),
                        Throwable::printStackTrace,
                        () -> System.out.println("\nKopylova \nПоток пустой"));
    }
}



