package com.schneewind.timetracking.timetracking.log;

import com.schneewind.timetracking.base.NestedStringConversion;
import com.schneewind.timetracking.base.StringConversion;

/**
 * a class for constructing unfinished log entries from strings and vice versa
 */
public class UnfinishedLogEntryStringConverter extends NestedStringConversion<UnfinishedLogEntry> {
    final String inActivityMarker="inactive";

    /**
     * default constructor
     * @param separator the char used for separating the fields
     * @param inActivityMarker a char for indicating that this unfinished log entry is not active
     */
    public UnfinishedLogEntryStringConverter(String separator, String enterNextLayer, String exitNextLayer) {
        super(separator, enterNextLayer, exitNextLayer);
    }

    @Override
    public String convertToString(UnfinishedLogEntry object) {
        StringBuilder stringBuilder = new StringBuilder();

        if(object.isActive()){
            stringBuilder.append(object.getInfo()).append(separator);
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
            String[] strings = string.split(separator);

            unfinishedLogEntry.start(strings[0], Long.parseLong(strings[1]));
        }

        return unfinishedLogEntry;
    }
}
