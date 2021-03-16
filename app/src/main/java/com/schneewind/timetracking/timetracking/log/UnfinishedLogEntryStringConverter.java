package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConverter;

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
    public String convertToString(UnfinishedLogEntry object) {
        StringBuilder stringBuilder = new StringBuilder();

        if(object.isActive()){
            stringBuilder.append(object.getInfo()).append(getSeparator());
            stringBuilder.append(object.getStartingTime());
        } else{
            stringBuilder.append(inActivityMarker);
        }
        return stringBuilder.toString();
    }


    /**
     * A function that converts a given string to an unfinished log entry
     * @param string
     * @return
     */
    @Override
    public UnfinishedLogEntry convertFromString(String string) {
        UnfinishedLogEntry unfinishedLogEntry = UnfinishedLogEntry.create();

        if(!string.equals(String.valueOf(inActivityMarker))){
            String[] strings = string.split(String.valueOf(getSeparator()));

            unfinishedLogEntry.start(strings[0], Long.parseLong(strings[1]));
        }

        return unfinishedLogEntry;
    }

    @Override
    protected List<String> toStringSequence(UnfinishedLogEntry object) {
        return null;//TODO
    }

    @Override
    protected UnfinishedLogEntry fromStringSequence(List<String> strings) {
        return null; //TODO
    }
}
