package net.kyrin.air.lib.command;

import net.kyrin.air.lib.AirKyrinLibary;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.ParsedLine;
import org.jline.reader.impl.DefaultHighlighter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public final class CommandLoader implements Runnable {

    private final LineReader lineReader;
    private Terminal terminal;
    private CloudCommandManager cloudCommandManager = AirKyrinLibary.getInstance().getCloudCommandManager();

    public CommandLoader() {
        try {
            terminal = TerminalBuilder.builder()
                    .system(false)
                    .streams(System.in, System.out)
                    .signalHandler(Terminal.SignalHandler.SIG_IGN)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .highlighter(new DefaultHighlighter())
                .history(new DefaultHistory())
                .build();
    }

    @Override
    public void run() {
        String line;
        while (true) {
            line = lineReader.readLine(">");
            line = line.trim();
            ParsedLine parsedLine = lineReader.getParser().parse(line, 0);
            CloudCommand command = cloudCommandManager.getCommand(parsedLine.word());
            if (command != null) {
                command.executeCommand(parsedLine.words().subList(1, parsedLine.words().size()).toArray(new String[0]));
                continue;
            }
            System.out.println("This command doesn't exist!");
        }
    }
}
