package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.StringConversion;

/**
 * A class for constructing log entries from strings and vice versa
 */
public class LogEntryStringConverter implements StringConversion<LogEntry> {
    final char separator;

    /**
     * default constructor for a log entry string converter
     * @param separator the character to be used for separating fields
     */
    public LogEntryStringConverter(char separator) {
        this.separator = separator;
    }

    /**
     * generates a string containing all data of a given log entry object
     * @param object the log entry to convert
     * @return
     */
    @Override
    public String convertToString(LogEntry object) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(object.getInfo() + getSeparator());
        stringBuilder.append(object.getStartTime() + getSeparator());
        stringBuilder.append(object.getStopTime() + getSeparator());

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
    public char getSeparator() {
        return separator;
    }
}
