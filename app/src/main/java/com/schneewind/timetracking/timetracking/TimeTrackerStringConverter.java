package com.schneewind.timetracking.timetracking;

import com.schneewind.timetracking.base.NestedStringConverter;
import com.schneewind.timetracking.base.StringConversion;
import com.schneewind.timetracking.timetracking.log.LogStringConverter;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntryStringConverter;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class TimeTrackerStringConverter extends NestedStringConverter<TimeTracker> {

    /**
     * default constructor for a TimeTrackerStringConverter
     * @param enterNextLayer a String used for marking the beginning of a nested object
     * @param exitNextLayer a String used for marking the ending of a nested object
     */
    public TimeTrackerStringConverter(String separator, String enterNextLayer, String exitNextLayer) {
        super();
        List<StringConversion> nestedConverters = Arrays.asList( new UnfinishedLogEntryStringConverter(),new LogStringConverter());
    }

    //TODO
    @Override
    public String convertToString(TimeTracker object) {
        StringBuilder result = new StringBuilder();




        return null;
    }

    @Override
    public TimeTracker convertFromString(String string) {
        return null;
    }

    @Override
    protected List<String> toStringSequence(TimeTracker object) {
        return null;//TODO
    }

    @Override
    protected TimeTracker fromStringSequence(List<String> strings) {
        return null; //TODO
    }
}
