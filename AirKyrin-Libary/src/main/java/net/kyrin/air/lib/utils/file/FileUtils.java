package net.kyrin.air.lib.utils.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static Path getPathByName(Path configPath, String name) {
        return Paths.get(configPath.toAbsolutePath().toString(), name + ".kyrinconf");
    }

    public static void downloadFileFromUrl(String fileUrl, String goalPath) throws IOException {
        URL url = new URL(fileUrl);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream outputStream = new FileOutputStream(goalPath);
        outputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        outputStream.close();
        readableByteChannel.close();
    }

    public static void copyFile(Path fileToCopy, Path destPath) {
        try {
            org.zeroturnaround.zip.commons.FileUtils.copyFileToDirectory(fileToCopy.toFile(), destPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createPath(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}