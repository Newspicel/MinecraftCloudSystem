package net.kyrin.air.lib;

import lombok.Getter;
import net.kyrin.air.lib.command.CloudCommandManager;
import net.kyrin.air.lib.console.StringUtil;
import org.apache.log4j.*;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.io.IoBuilder;
import org.apache.logging.log4j.io.LoggerOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

@Getter
public class AirKyrinLibary {

    private static AirKyrinLibary instance;

    private Logger consoleLogger;
    private CloudCommandManager cloudCommandManager;

    public AirKyrinLibary(String folder, String loggerName) {
        instance = this;
        this.cloudCommandManager = new CloudCommandManager();
        this.consoleLogger = LogManager.getLogger(loggerName);
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(new EnhancedPatternLayout("%d{HH:mm:ss,SSS} [%t] (%p) %c - %m%n"));
        consoleAppender.setTarget(ConsoleAppender.SYSTEM_OUT);
        consoleAppender.activateOptions();
        System.setOut(IoBuilder.forLogger(loggerName).setLevel(org.apache.logging.log4j.Level.OFF).buildPrintStream());
        System.setErr(IoBuilder.forLogger(loggerName).setLevel(org.apache.logging.log4j.Level.ERROR).buildPrintStream());
        consoleLogger.addAppender(consoleAppender);
        consoleLogger.info("Test");
        System.out.println("Test1");
        System.err.println("Teee");
    }

    public static AirKyrinLibary getInstance() {
        return instance;
    }

    private String getBigName() {
        return
                        "            _      _  __          _       \n" +
                        "     /\\   (_)    | |/ /         (_)      \n" +
                        "    /  \\   _ _ __| ' /_   _ _ __ _ _ __  \n" +
                        "   / /\\ \\ | | '__|  <| | | | '__| | '_ \\ \n" +
                        "  / ____ \\| | |  | . \\ |_| | |  | | | | |\n" +
                        " /_/    \\_\\_|_|  |_|\\_\\__, |_|  |_|_| |_|\n" +
                        "                       __/ |             \n" +
                        "                      |___/              \n";
    }

    public void sendHeader() {
        System.out.println(" ");
        System.out.println(getBigName());
        System.out.println(" AirKyrin #" + StringUtil.AIRKYRIN_SPECIFICATION + " V" + StringUtil.AIRKYRIN_VERSION);
        System.out.println(" Copyright © 2019 KyrinNetwork | All rights reserved");
        System.out.println(" ");
        System.out.println(" AirKyrin is running on " + StringUtil.OS_NAME + " (" + StringUtil.USER_NAME + StringUtil.SLASH + StringUtil.OS_VERSION + ") with architecture " + StringUtil.OS_ARCH);
        System.out.println(" Java: " + StringUtil.JAVA_VERSION + " (Recommend: Java 1.8.0 or higher)");
        System.out.println(" ");
    }
}
