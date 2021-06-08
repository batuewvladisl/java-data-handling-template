package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {

        Path pathResult = Paths.get(path);
        if (!pathResult.isAbsolute()) {

            URL url = getClass().getClassLoader().getResource(path);
            try {
                assert url != null;
                Path pathAbsolut = Paths.get(url.toURI());
                path = pathAbsolut.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File dir = new File(path);
        long countFiles = 0;
        if (dir.exists() && dir.isDirectory()) {
            for (File innerDir: Objects.requireNonNull(dir.listFiles())) {
                if (innerDir.isFile()) {
                    countFiles++;
                } else {
                    countFiles += new SimpleFileRepository().countFilesInDirectory(innerDir.getPath());
                }
            }
        }
        return countFiles;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {

        Path pathResult = Paths.get(path);
        if (!pathResult.isAbsolute()) {

            URL url = getClass().getClassLoader().getResource(path);
            try {
                assert url != null;
                Path pathAbsolut = Paths.get(url.toURI());
                path = pathAbsolut.toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File dir = new File(path);
        long countDirs = 1;
        if (dir.exists() && dir.isDirectory()) {
            for (File innerDir: Objects.requireNonNull(dir.listFiles())) {
                if (innerDir.isDirectory()) {
                    countDirs += new SimpleFileRepository().countDirsInDirectory(innerDir.getPath());
                }
            }
        }
        return countDirs;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {

        File dirFrom = new File(from);
        File dirTo = new File(to);
        dirTo.getParentFile().exists();
        try {
            Files.copy(dirFrom.getAbsoluteFile().toPath(), dirTo.getAbsoluteFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        String targetFolder = "target/classes/";
        File dir = new File(targetFolder + path);
        dir.mkdir();
        File file = new File(dir.getPath() + File.separator + name);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {

        StringBuilder textFromFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/resources"+File.separator+fileName))){
            while (reader.ready()) {
                textFromFile.append(reader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return textFromFile.toString();
    }
}
