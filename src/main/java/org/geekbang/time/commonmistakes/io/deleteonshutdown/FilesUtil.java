package org.geekbang.time.commonmistakes.io.deleteonshutdown;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class FilesUtil {
    private static LinkedHashSet<Path> files = new LinkedHashSet<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(FilesUtil::shutdownHook));
    }

    private static void shutdownHook() {
        ArrayList<Path> toBeDeleted = new ArrayList<>(files);
        toBeDeleted.forEach(path -> {
            try {
                Files.delete(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized void deleteOnExit(Path p) {
        files.add(p);
    }
}
