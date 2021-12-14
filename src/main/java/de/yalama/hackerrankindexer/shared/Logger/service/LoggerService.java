package de.yalama.hackerrankindexer.shared.Logger.service;

import de.yalama.hackerrankindexer.shared.Logger.model.LogItem;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * Experimental class because log4j
 */
public abstract class LoggerService {

    /**
     * Logs a message with optional objects as parameters
     * Logging means a LogItem instance is created
     * and added to the list
     * @param message base message with optional elements for string interpolation
     * @param objects optional: Objects that can be passed
     */
    public abstract void log(String message, Object ... objects);

    /**
     * Basically what log does but also throws an exception afterwads
     * @param message see log
     * @param exception The exception to throw
     * @param objects see log
     */
    public abstract void exception(String message, Exception exception, Object ... objects) throws Exception;

    /**
     * Returns a collection of LogItem items from a start (included) date to end date (included)
     * @param start the start date
     * @param end the end date
     * @return A collection of LogItem items (List for order)
     */
    public abstract Collection<LogItem> getLogsFromTo(Date start, Date end);

    /**
     * Returns a collection of LogItem items from a start (included) date
     * @param start the start date
     * @return A collection of LogItem items (List for order)
     */
    public abstract Collection<LogItem> getLogsFrom(Date start);

    /**
     * Returns a collection of LogItem items to end date (included)
     * @param end the end date
     * @return A collection of LogItem items (List for order)
     */
    public abstract Collection<LogItem> getLogsTo(Date end);

    /**
     * Returns a collection of all saved logs
     * @return A collection of LogItem items (List for order)
     */
    public abstract Collection<LogItem> getAllLogs();

    /**
     * wipes all saved logs
     */
    public abstract void wipeAllLogs();

    /**
     * Wipes all saved logs between start and end date (included)
     * @param start start date
     * @param end end date
     */
    public abstract void wipeLogsFromTo(Date start, Date end);

    /**
     * Wipes all saved logs until end date
     * @param end end date
     */
    public abstract void wipeLogsTo(Date end);

    /**
     * Wipes all logs from start date
     * @param start start date
     */
    public abstract void wipeLogsFrom(Date start);

    /**
     * Writes all logs to a file
     * @param logs the logs to write to file
     */
    public abstract void writeLogsToFile(Collection<LogItem> logs) throws IOException;

    /**
     * Writes all saved logs to file
     */
    public abstract void writeAllLogsToFile() throws IOException;

    /**
     * writes logs from start date to end date to file (included)
     * @param start start date
     * @param end end date
     */
    public abstract void writeAllLogsFromToToFile(Date start , Date end) throws IOException;

    /**
     * Writes all saved logs from start date to file
     * @param start start date
     */
    public abstract void writeLogsFromToFile(Date start) throws IOException;

    /**
     * Writes logs to end date to file
     * @param end end date
     */
    public abstract void writeLogsToToFile(Date end);
}
