package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConverter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A class for constructing log entries from strings and vice versa
 */
public class LogEntryStringConverter extends NestedStringConverter<LogEntry> {
    private final char noInfoMarker;


    /**
     * default constructor for a log entry string converter
     */
    public LogEntryStringConverter(char noInfoMarker) {
        super();
        this.noInfoMarker = noInfoMarker;
    }

    @Override
    protected List<String> toStringSequence(LogEntry object) {
        List<String> sequence = new LinkedList<>();

        if(!object.getInfo().isEmpty()){
            sequence.add(sanitize(object.getInfo()));
        } else{
            sequence.add(String.valueOf(noInfoMarker));
        }
        sequence.add(sanitize(object.getStartTime()));
        sequence.add(sanitize(object.getStopTime()));

        return sequence;
    }

    @Override
    protected LogEntry fromStringSequence(List<String> strings) {
        String info = strings.get(0);
        if (info.equals("#")){
            info = "";
        }
        return new LogEntry(Long.parseLong(strings.get(1)), Long.parseLong(strings.get(2)),info);
    }

    @Override
    public Collection<String> getReservedStrings() {
        Collection<String> reservedStrings = super.getReservedStrings();
        //reservedStrings.add(String.valueOf(noInfoMarker));
        return reservedStrings;
    }
}
