package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public abstract class ACinemaLoggerAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {
    }

    public String toText(ILoggingEvent event) {
        return "["
                + "Level = "
                + event.getLevel().levelStr
                + " | " + "Class = "
                + event.getLoggerName()
                + " | " + "Thread = " + event.getThreadName()
                + " | " + "Message = " + event.getFormattedMessage() + " | " + " | " + "Time = "
                + (event.getTimeStamp() - event.getLoggerContextVO().getBirthTime()) + " | " + "]";
    }
}
