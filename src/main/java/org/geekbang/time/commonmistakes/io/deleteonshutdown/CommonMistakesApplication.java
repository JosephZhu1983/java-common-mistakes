package org.geekbang.time.commonmistakes.io.deleteonshutdown;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) throws Exception {
        file2();
    }

    private static void file() throws Exception {
        File file = new File("test.txt");
        file.createNewFile();
        file.deleteOnExit();
        System.out.println(file.exists());
        TimeUnit.SECONDS.sleep(5);
    }

    private static void file2() throws Exception {
        Path path = Paths.get("test2.txt");
        System.out.println(Files.exists(path));
        try {
            Files.createFile(path);
            System.out.println(Files.exists(path));
            try (BufferedReader in = Files.newBufferedReader(path, Charset.defaultCharset())) {
                try (BufferedWriter out = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.DELETE_ON_CLOSE)) {
                    out.append("Hello, World!");
                    out.flush();
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                System.out.println(Files.exists(path));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void file3() throws Exception {
        Path path = Paths.get("test3.txt");
        OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE);
        outputStream.write("test".getBytes());
        outputStream.close();
        FilesUtil.deleteOnExit(path);
        TimeUnit.SECONDS.sleep(5);
    }
}

