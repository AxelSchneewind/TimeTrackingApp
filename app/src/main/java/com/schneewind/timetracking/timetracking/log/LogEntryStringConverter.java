package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConversion;
import com.schneewind.timetracking.base.StringConversion;

/**
 * A class for constructing log entries from strings and vice versa
 */
public class LogEntryStringConverter extends NestedStringConversion<LogEntry> {

    /**
     * default constructor for a log entry string converter
     * @param separator the character to be used for separating fields
     */
    public LogEntryStringConverter(String separator, String enterNextLayer, String exitNextLayer) {
        super(separator, enterNextLayer, exitNextLayer);
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
}
