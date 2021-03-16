package com.schneewind.timetracking.timetracking;

import com.schneewind.timetracking.base.StringConversion;
import com.schneewind.timetracking.timetracking.log.LogStringConverter;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntryStringConverter;

import java.util.Arrays;
import java.util.Collection;

/**
 * A class that constructs TimeTrackers from strings and vice versa. It can be used to store TimeTrackers in text files
 * @author Axel Schneewind
 * */
@Deprecated
public class TimeTrackerStringConverterOld implements StringConversion<TimeTracker>{
    final char separator;

    final LogStringConverter logStringConverter;
    final UnfinishedLogEntryStringConverter unfinishedLogEntryStringConverter;

    /**
     * default constructor for a time tracker string converter
     * @param logEntrySeparator the char used to separate fields of log entries
     * @param logSeparator  the char used to separate fields of the log
     * @param separator the char to be used for separating fields of the timetracker
     */
    public TimeTrackerStringConverterOld(char separator, char logSeparator, char logEntrySeparator) {
        this.separator = separator;
        this.logStringConverter = new LogStringConverter('#');
        this.unfinishedLogEntryStringConverter = new UnfinishedLogEntryStringConverter();
    }


    //TODO implement functionality to  recognize old file versions and import them in a correct way
    /**
     * returns a string that can be saved in a text file
     * @return A string containing all the data of a TimeTracker
     */
    @Override
    public String convertToString(TimeTracker object) {
        //legacy file conversion
        //String formattedString = String.format("%s;%d;%d",object.getName(), object.getTime(),object.getTargetTime());
        //return formattedString;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(object.getName()).append(getSeparator());                                                                      //0 name
        stringBuilder.append(object.getTime()).append(getSeparator());                                                                      //1 time
        stringBuilder.append(object.getTargetTime()).append(getSeparator());                                                                //2 target time
        stringBuilder.append(object.isActive()).append(getSeparator());                                                                     //3 active
        stringBuilder.append(unfinishedLogEntryStringConverter.convertToString(object.getUnfinishedLogEntry())).append(getSeparator());     //4 unfinished log entry
        stringBuilder.append(logStringConverter.convertToString(object.getLog())).append(getSeparator());                                   //5 log

        return stringBuilder.toString();
    }

    /**
     * Constructs a TimeTracker from a saved String
     * @param string a string that has been read from a file
     * @return a TimeTracker with the data from the string
     */
    @Override
    public TimeTracker convertFromString(String string) {
        String[] segments = string.split(String.valueOf(getSeparator()));
        TimeTracker result = new TimeTracker(segments[0], Integer.parseInt(segments[1]), Integer.parseInt(segments[2]), logStringConverter.convertFromString(segments[5]), unfinishedLogEntryStringConverter.convertFromString(segments[4]));
        result.setActive(Boolean.parseBoolean(segments[3]));

        return result;
    }

    @Override
    public Collection<String> getReservedStrings() {
        return Arrays.asList(String.valueOf(getSeparator()));
    }

    public char getSeparator() {
        return separator;
    }
}
