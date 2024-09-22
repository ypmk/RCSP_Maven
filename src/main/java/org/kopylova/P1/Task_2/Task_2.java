package org.kopylova.P1.Task_2;

import java.util.Scanner;
import java.util.concurrent.*;

public class Task_2 {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            while (true) {

                System.out.print("Введите число (или 'exit' для выхода): ");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                int number = Integer.parseInt(userInput);
                Future<Integer> future = executorService.submit(() -> calculateSquare(number));
                try {
                    int result = future.get();
                    System.out.println("Результат: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }
        }
    }

    private static int calculateSquare(int number) {
        int delayInSeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delayInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return number * number;
    }
}
