package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConverter;

import java.util.List;

/**
 * A class for constructing log entries from strings and vice versa
 */
public class LogEntryStringConverter extends NestedStringConverter<LogEntry> {

    /**
     * default constructor for a log entry string converter
     */
    public LogEntryStringConverter() {
        super();
    }

    /**
     * generates a string containing all data of a given log entry object
     * @param object the log entry to convert
     * @return
     */
    @Override
    public String convertToString(LogEntry object) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(object.getInfo()).append(getSeparator());
        stringBuilder.append(object.getStartTime()).append(getSeparator());
        stringBuilder.append(object.getStopTime());

        return stringBuilder.toString();
    }

    /**
     * constructs a log entry object from a given string
     * @param string
     * @return
     */
    @Override
    public LogEntry convertFromString(String string) {
        String[] strings = string.split(String.valueOf(getSeparator()));

        return new LogEntry(Long.parseLong(strings[1]), Long.parseLong(strings[2]),strings[0]);
    }


    @Override
    protected List<String> toStringSequence(LogEntry object) {
        return null;
        //TODO
    }

    @Override
    protected LogEntry fromStringSequence(List<String> strings) {
        return null;
        //TODO
    }
}
