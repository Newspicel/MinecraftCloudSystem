package net.kyrin.air.lib.console;

import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.*;

@Getter
public class ConsoleLogger extends Logger {

    private ConsoleReader consoleReader;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    /**
     * Creates a new instance of the {@link ConsoleLogger}
     */
    public ConsoleLogger(String folder) {
        super("AirKyrinLogger", null);

        Field field;
        try {
            field = Charset.class.getDeclaredField("defaultCharset");

            field.setAccessible(true);
            field.set(null, StandardCharsets.UTF_8);

            setLevel(Level.OFF);
            setUseParentHandlers(false);

            this.consoleReader = new ConsoleReader(System.in, System.out);
            this.consoleReader.setExpandEvents(false);

            if (!Files.exists(Paths.get(folder)))
                Files.createDirectory(Paths.get(folder));

            if (!Files.exists(Paths.get(folder, "logs")))
                Files.createDirectory(Paths.get(folder, "logs"));

            AnsiConsole.systemInstall();

            final File file = new File(folder + "/", "logs/");
            final SimpleFormatter simpleFormatter = new SimpleFormatter();

            FileHandler fileHandler = new FileHandler(file.getCanonicalPath() + "/CloudLog", 5242880, 100, false);
            fileHandler.setEncoding(StandardCharsets.UTF_8.name());
            fileHandler.setFormatter(simpleFormatter);

            addHandler(fileHandler);

        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print an info message to the console
     *
     * @param message
     */
    public void info(String message) {
        super.log(Level.INFO, message);
        try {
            this.consoleReader.println("(INFO) [" + dateFormat.format(System.currentTimeMillis()) + "] " + message);
            this.complete();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints a warning to the console
     *
     * @param message
     */
    public void warn(String message) {
        super.log(Level.WARNING, message);
        try {
            this.consoleReader.println("(WARN) [" + dateFormat.format(System.currentTimeMillis()) + "] " + message);
            this.complete();

        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints an error to the console
     *
     * @param message
     */
    public void err(String message) {
        super.log(Level.SEVERE, message);
        try {
            this.consoleReader.println("(ERROR) [" + dateFormat.format(System.currentTimeMillis()) + "] " + message);
            this.complete();

        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Clears the screen with the {@link ConsoleReader}
     */
    public void clearScreen() {
        try {
            this.consoleReader.clearScreen();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints an empty line to the console
     *
     * @return this
     */
    public ConsoleLogger emptyLine() {
        try {
            this.consoleReader.println(" ");
            this.complete();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        return this;
    }

    /**
     * Flush the {@link ConsoleReader}
     */
    public void flush() {
        try {
            this.consoleReader.flush();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Complete the print line
     * <p>
     * {@link ConsoleReader}
     */
    public void complete() {
        try {
            this.consoleReader.drawLine();
            this.consoleReader.flush();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the {@link ConsoleReader} and closes all {@link Handler}
     */
    public void shutdownAll() {
        try {
            this.consoleReader.drawLine();
            this.consoleReader.flush();

            for (Handler handler : this.getHandlers())
                handler.close();

            this.consoleReader.killLine();
            this.consoleReader.close();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }


}
