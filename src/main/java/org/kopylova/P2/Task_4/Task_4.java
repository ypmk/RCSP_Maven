package org.kopylova.P2.Task_4;

import java.io.*;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Task_4 {
    private static Map<Path, List<String>> fileContentsMap = new HashMap<>();
    private static Map<Path, String> fileHashes = new HashMap<>();
    public static void main(String[] args) throws IOException, InterruptedException {
        Path d = Paths.get("./Zad4_Base");
        // Проверяем, существует ли каталог
        if (!Files.exists(d)) {
            try {
                // Создаем каталог, если его не существует
                Files.createDirectory(d);
                System.out.println("Каталог создан: " + d.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Ошибка при создании каталога: " + e.getMessage());
            }
        } else {
            System.out.println("Каталог уже существует: " + d.toAbsolutePath());
        }
        Path directory = Paths.get("./Zad4_Base");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        firstObserve(directory);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path filePath = (Path) event.context();
                    System.out.println("Создан новый файл: " + filePath);
                    fileContentsMap.put(filePath, readLinesFromFile(directory.resolve(filePath)));
                    calculateFileHash(directory.resolve(filePath));
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path filePath = (Path) event.context();
                    System.out.println("Файл изменен: " + filePath);
                    detectFileChanges(directory.resolve(filePath));
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path filePath = (Path) event.context();
                    System.out.println("Удален файл: " + filePath);
                    String hash = fileHashes.get(directory.resolve(filePath));
                    if (hash != null) {
                        System.out.println("Хеш-сумма удаленного файла: " +
                                hash);
                    }
                }
            }
            key.reset();
        }
    }
    private static void firstObserve(Path directory) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory))
        {
            for (Path filePath : stream) {
                if (Files.isRegularFile(filePath)) {
                    fileContentsMap.put(filePath, readLinesFromFile(filePath));
                    calculateFileHash(filePath);
                }
            }
        }
    }
    private static void detectFileChanges(Path filePath) throws IOException {
        List<String> newFileContents = readLinesFromFile(filePath);
        List<String> oldFileContents = fileContentsMap.get(filePath);
        if (oldFileContents != null) {
            List<String> addedLines = newFileContents.stream()
                    .filter(line -> !oldFileContents.contains(line))
                    .toList();
            List<String> deletedLines = oldFileContents.stream()
                    .filter(line -> !newFileContents.contains(line))
                    .toList();
            if (!addedLines.isEmpty()) {
                System.out.println("Добавленные строки в файле " + filePath +
                        ":");
                addedLines.forEach(line -> System.out.println("+ " + line));
            }
            if (!deletedLines.isEmpty()) {
                System.out.println("Удаленные строки из файла " + filePath +
                        ":");
                deletedLines.forEach(line -> System.out.println("- " + line));
            }
        }
        calculateFileHash(filePath);
        fileContentsMap.put(filePath, newFileContents);
    }
    private static List<String> readLinesFromFile(Path filePath) throws
            IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    private static void calculateFileHash(Path filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = Files.newInputStream(filePath);
                 DigestInputStream dis = new DigestInputStream(is, md)) {
                while (dis.read() != -1) ;
                String hash = bytesToHex(md.digest());
                fileHashes.put(filePath, hash);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
