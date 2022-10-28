package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import org.springframework.stereotype.Component;

@Component
public class CinemaLoggerAppenderConsole extends ACinemaLoggerAppender {

    protected ConsoleTarget target = ConsoleTarget.SystemOut;

    @Override
    protected void append(ILoggingEvent eventObject) {
        System.out.println(this.toText(eventObject));
    }
}
