package org.kopylova.P3.Task_4_old;

public class FileProcessingSystem {
    public static void main(String[] args) {
        int queueCapacity = 5;
        FileQueue fileQueue = new FileQueue(queueCapacity);
        String[] supportedFileTypes = {"XML", "JSON", "XLS"};
        for (String fileType : supportedFileTypes) {
            new FileProcessor(fileType)
                    .processFiles(fileQueue.getFileObservable())
                    .subscribe(
                            () -> {}, // Обработка успешного завершения
                            throwable -> System.err.println("Error processing file: " + throwable)
                    );

        }
        // Даем системе время для работы (можно изменить)
        try {
            Thread.sleep(10000); // Пусть система работает 10 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
