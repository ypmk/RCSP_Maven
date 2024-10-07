package org.kopylova.P3.Task_4_old;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

// Обработчик файлов
class FileProcessor {
    private final String supportedFileType;
    // Создает обработчик файлов для определенного типа файлов
    public FileProcessor(String supportedFileType) {
        this.supportedFileType = supportedFileType;
    }
    // Обрабатывает файлы асинхронно с задержкой
    public Completable processFiles(Observable<File> fileObservable) {
        return fileObservable
                .filter(file -> file.getFileType().equals(supportedFileType)) // Фильтрует файлы по типу
 .flatMapCompletable(file -> {
            long processingTime = file.getFileSize() * 7; // Вычисляет время обработки
            return Completable
                    .fromAction(() -> {
                        Thread.sleep(processingTime); // Имитация обработки файла
                        System.out.println("Processed " +
                                supportedFileType + " file with size " + file.getFileSize());
                    })
                    .subscribeOn(Schedulers.io()) // Выполняется в фоновом потоке
 .observeOn(Schedulers.io()); // Результаты наблюдаются в фоновом потоке
        })
                .onErrorComplete(); // Игнорирует ошибки и завершает успешно
    }
}