package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CinemaLoggerAppenderFile extends ACinemaLoggerAppender {


    @Override
    protected void append(ILoggingEvent eventObject) {
//        Path root = Paths.get(".").normalize().toAbsolutePath();
        Path root = Paths.get("/var/log").normalize().toAbsolutePath();
        String path = root + "/logger.txt";
        File file = new File(path);
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException();
                }
            }
            String content = this.toText(eventObject);
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
