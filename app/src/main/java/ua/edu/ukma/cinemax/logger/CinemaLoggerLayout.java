package ua.edu.ukma.cinemax.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class CinemaLoggerLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent event) {
        return "[" + "Level = " + event.getLevel().levelStr + " | " + "Class = " + event.getLoggerName() + " | "
                + "Thread = " + event.getThreadName() + " | " + "Message = " + event.getFormattedMessage()
                + " | " + " | " + "Time = " + (event.getTimeStamp() - event.getLoggerContextVO().getBirthTime())
                + " | " + "]\n";
    }
}
