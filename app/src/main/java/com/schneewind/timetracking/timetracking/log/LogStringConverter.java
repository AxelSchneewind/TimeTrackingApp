package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConverter;
import com.schneewind.timetracking.base.StringConversion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for constructing timetracking logs from strings and vice versa
 * @author Axel Schneewind
 */
public class LogStringConverter extends NestedStringConverter<Log> {
    private final StringConversion<LogEntry> logEntryStringConverter;

    /**
     * default constructor for a log string converter
     */
    public LogStringConverter(char noInfoMarker) {
        super();
        this.logEntryStringConverter = new LogEntryStringConverter(noInfoMarker);
    }


    @Override
    protected List<String> toStringSequence(Log object) {
        List<String> sequence = new LinkedList<>();

        for(LogEntry entry : object.getLogEntries()){
            sequence.add(getGoDeeper() + logEntryStringConverter.convertToString(entry) + getGoUp());
        }

        return sequence;
    }

    @Override
    protected Log fromStringSequence(List<String> strings) {
        List<LogEntry> entries = new LinkedList<>();

        for(String entry  : strings){
            entries.add(logEntryStringConverter.convertFromString(entry));
        }

        return new Log(entries);
    }
}
