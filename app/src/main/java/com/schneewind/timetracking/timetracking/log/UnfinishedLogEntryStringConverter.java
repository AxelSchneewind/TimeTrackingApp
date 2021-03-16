package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConverter;

import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;

/**
 * a class for constructing unfinished log entries from strings and vice versa
 */
public class UnfinishedLogEntryStringConverter extends NestedStringConverter<UnfinishedLogEntry> {
    final String inActivityMarker="inactive";

    /**
     * default constructor
     */
    public UnfinishedLogEntryStringConverter() {
        super();
    }

    @Override
    protected List<String> toStringSequence(UnfinishedLogEntry object) {
        List<String> sequence = new LinkedList<>();

        if(object.isActive()){
            sequence.add(sanitize(object.getInfo()));
            sequence.add(sanitize(object.getStartingTime()));
        } else{
            sequence.add(inActivityMarker);
        }

        return sequence;//TODO
    }

    @Override
    protected UnfinishedLogEntry fromStringSequence(List<String> strings) {
        UnfinishedLogEntry entry = UnfinishedLogEntry.create();
        if(strings.size() == 2) {
            entry.start(strings.get(0), Long.parseLong(strings.get(1)));
        }
        return entry;
        //TODO
    }
}
