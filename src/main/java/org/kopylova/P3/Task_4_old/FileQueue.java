package org.kopylova.P3.Task_4_old;

import io.reactivex.Observable;

public class FileQueue {
    private final int capacity;
    private final Observable<File> fileObservable;
    // Создает очередь с заданной вместимостью и подключается к генератору файлов
    public FileQueue(int capacity) {
        this.capacity = capacity;

        this.fileObservable = new FileGenerator().generateFile()
                .replay(capacity) // Буферизирует источник файлов с ограниченной емкостью
                .autoConnect(); // Подключается автоматически к буферизированному источнику
    }
    // Получает наблюдаемый поток файлов
    public Observable<File> getFileObservable() {
        return fileObservable;
    }
}

