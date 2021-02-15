package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.StringConversion;

/**
 * a class for constructing unfinished log entries from strings and vice versa
 */
public class UnfinishedLogEntryStringConverter implements StringConversion<UnfinishedLogEntry> {
    final char separator;
    final char inActivityMarker;

    /**
     * default constructor
     * @param separator the char used for separating the fields
     * @param inActivityMarker a char for indicating that this unfinished log entry is not active
     */
    public UnfinishedLogEntryStringConverter(char separator, char inActivityMarker) {
        this.separator = separator;
        this.inActivityMarker = inActivityMarker;
    }

    @Override
    public String convertToString(UnfinishedLogEntry object) {
        StringBuilder stringBuilder = new StringBuilder();

        if(object.isActive()){
            stringBuilder.append(object.getInfo());
            stringBuilder.append(separator);
            stringBuilder.append(object.getStartingTime());
        } else{
            stringBuilder.append(inActivityMarker);
        }
        return stringBuilder.toString();
    }

    @Override
    public UnfinishedLogEntry convertFromString(String string) {
        UnfinishedLogEntry unfinishedLogEntry = UnfinishedLogEntry.create();

        if(!string.equals(inActivityMarker)){
            String[] strings = string.split(String.valueOf(getSeparator()));

            unfinishedLogEntry.start(strings[0], Long.parseLong(strings[1]));
        }

        return unfinishedLogEntry;
    }

    @Override
    public char getSeparator() {
        return separator;
    }


}
