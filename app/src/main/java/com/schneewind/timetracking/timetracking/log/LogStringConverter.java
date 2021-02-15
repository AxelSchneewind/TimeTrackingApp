package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.StringConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for constructing timetracking logs from strings and vice versa
 * @author Axel Schneewind
 */
public class LogStringConverter implements StringConversion<Log> {
    final LogEntryStringConverter logEntryStringConverter;

    final char separator;

    /**
     * default constructor for a log string converter
     * @param logEntrySeparator the char to be used for separating the fields of log entries
     * @param logSeparator the char used for separating log entries
     */
    public LogStringConverter(char logSeparator, char logEntrySeparator) {
        this.logEntryStringConverter = new LogEntryStringConverter(logEntrySeparator);
        this.separator = logSeparator;
    }

    /**
     * generates a string containing all the data stored in a given Log object
     * @param object the Log to convert
     * @return
     */
    @Override
    public String convertToString(Log object) {
        StringBuilder stringBuilder = new StringBuilder();

        for(LogEntry entry : object.getLogEntries()){
            stringBuilder.append(logEntryStringConverter.convertToString(entry));
            stringBuilder.append(getSeparator());
        }

        return stringBuilder.toString();
    }

    /**
     * constructs a Log object from a given String
     * @param string the string to convert
     * @return the constructed log object
     */
    @Override
    public Log convertFromString(String string) {
        List<LogEntry> entries = new ArrayList<>();

        String[] entryStrings = string.split(String.valueOf(getSeparator()));

        for(String entry  : entryStrings){
            entries.add(logEntryStringConverter.convertFromString(entry));
        }

        return new Log(entries);
    }

    @Override
    public char getSeparator() {
        return separator;
    }
}
