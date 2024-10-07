package org.kopylova.P3.Task_4;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class FileProcessingSystem {
    public static void main(String[] args) {
        System.out.println("Kopylova");
        FileGenerator generator = new FileGenerator();

        // Создаем очередь файлов с буферизацией
        Observable<List<File>> fileQueue = generator.generateFiles()
                .doOnNext(file -> System.out.println("Сгенерирован файл: " + file))
                .buffer(5); // Буферизация по 5 элементов

        // Создаем обработчики для разных типов файлов
        FileProcessor xmlProcessor = new FileProcessor("XML");
        FileProcessor jsonProcessor = new FileProcessor("JSON");
        FileProcessor xlsProcessor = new FileProcessor("XLS");

        // Параллельная обработка файлов
        fileQueue
                .observeOn(Schedulers.io())
                .subscribe(files -> {
                    // Обрабатываем файлы из буфера
                    for (File file : files) {
                        if (file.getType().equals("XML")) {
                            xmlProcessor.processFile(file);
                        } else if (file.getType().equals("JSON")) {
                            jsonProcessor.processFile(file);
                        } else if (file.getType().equals("XLS")) {
                            xlsProcessor.processFile(file);
                        }
                    }
                }, Throwable::printStackTrace);

        // Ожидание для демонстрации работы
        try {
            Thread.sleep(10000); // 10 секунд работы программы
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

