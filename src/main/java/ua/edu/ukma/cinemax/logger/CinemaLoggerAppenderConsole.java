package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.stereotype.Component;

@Component
public class CinemaLoggerAppenderConsole extends ACinemaLoggerAppender {

    @Override
    protected void append(ILoggingEvent eventObject) {
        System.out.println(this.toText(eventObject));
    }
}
