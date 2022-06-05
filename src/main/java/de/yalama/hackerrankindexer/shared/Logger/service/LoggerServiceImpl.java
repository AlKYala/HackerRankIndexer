package de.yalama.hackerrankindexer.shared.Logger.service;

import de.yalama.hackerrankindexer.shared.Logger.model.LogItem;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggerServiceImpl extends LoggerService{

    private List<LogItem> logItemList;

    public LoggerServiceImpl() {
        this.logItemList = new ArrayList<LogItem>();
    }

    @Override
    public void log(String message, Object... objects) {
        String logMessage = String.format(message, objects);
        LogItem entry = new LogItem(logMessage);
        this.logItemList.add(entry);
    }

    @Override
    public void exception(String message, Exception exception, Object... objects) throws Exception {
        String modifiedMessage = String.format("Throwing exception: %s - message: %s", exception.getLocalizedMessage(), message);
        this.log(message, objects);
        throw exception;
    }

    @Override
    public Collection<LogItem> getLogsFromTo(Date start, Date end) {
        return this.logItemList
                .stream()
                .filter(entry -> entry.getDate().after(start) && entry.getDate().before(end))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<LogItem> getLogsFrom(Date start) {
        //return this.logItemList.stream().filter(entry -> entry.getDate().after(start)).collect(Collectors.toList());
        return this.getLogsFromTo(start, new Date());
    }

    @Override
    public Collection<LogItem> getLogsTo(Date end) {
        //return this.logItemList.stream().filter(entry -> entry.getDate().before(end)).collect(Collectors.toList());
        return this.getLogsFromTo(this.getOldDate(), end);
    }

    @Override
    public Collection<LogItem> getAllLogs() {
        return this.logItemList;
    }

    @Override
    public void wipeAllLogs() {
        this.logItemList.clear();
    }

    @Override
    public void wipeLogsFromTo(Date start, Date end) {
        this.logItemList.removeIf(entry -> entry.getDate().after(start) && entry.getDate().before(end));
    }

    @Override
    public void wipeLogsTo(Date end) {
        //this.logItemList.removeIf(entry -> entry.getDate().before(end));
        this.wipeLogsFromTo(this.getOldDate(), end);
    }

    @Override
    public void wipeLogsFrom(Date start) {
        this.wipeLogsFromTo(start, new Date());
    }

    @Override
    public void writeLogsToFile(Collection<LogItem> logs) throws IOException {
        String fileName = String.format("logs_%s.txt", new Date().toString());
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for(LogItem item : this.logItemList) {
            writer.write(String.format("%s\n", item.toString()));
        }
        writer.close();
    }

    @Override
    public void writeAllLogsToFile() throws IOException {
        this.writeLogsToFile(this.logItemList);
    }

    @Override
    public void writeAllLogsFromToToFile(Date start, Date end) throws IOException {
        this.writeLogsToFile(this.getLogsFromTo(start, end));
    }

    @Override
    public void writeLogsFromToFile(Date start) throws IOException {
        this.writeLogsToFile(this.getLogsFrom(start));
    }

    @Override
    public void writeLogsToToFile(Date end) {
        this.writeLogsToToFile(end);
    }

    private Date getOldDate() {
        return new Date("1970-01-01");
    }
}
