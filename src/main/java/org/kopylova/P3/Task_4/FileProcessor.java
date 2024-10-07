package org.kopylova.P3.Task_4;

public class FileProcessor {
    private final String fileType;

    public FileProcessor(String fileType) {
        this.fileType = fileType;
    }

    public void processFile(File file) {
        if (file.getType().equals(fileType)) {
            System.out.println("Обработка файла: " + file);
            try {
                // Время обработки пропорционально размеру файла
                Thread.sleep(file.getSize() * 7);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Файл обработан: " + file);
        }
    }
}


