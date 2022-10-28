package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract public class ACinemaLoggerAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject){}

    public String toText(ILoggingEvent event) {
        return "[" +
                "Level = " +
                event.getLevel().levelStr +
                " | " +
                "Class = " +
                event.getLoggerName() +
                " | " +
                "Thread = " +
                event.getThreadName() +
                " | " +
                "Message = " +
                event.getFormattedMessage() +
                " | " +
                " | " +
                "Time = " +
                (event.getTimeStamp() - event.getLoggerContextVO().getBirthTime()) +
                " | " +
                "]";
    }
}
