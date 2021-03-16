package com.schneewind.timetracking.timetracking;

import com.schneewind.timetracking.base.NestedStringConverter;
import com.schneewind.timetracking.base.StringConversion;
import com.schneewind.timetracking.timetracking.log.Log;
import com.schneewind.timetracking.timetracking.log.LogStringConverter;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntry;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntryStringConverter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class TimeTrackerStringConverter extends NestedStringConverter<TimeTracker> {

    /**
     * default constructor for a TimeTrackerStringConverter
     */
    public TimeTrackerStringConverter() {
        super(Arrays.asList(new UnfinishedLogEntryStringConverter(),new LogStringConverter('#')));
    }

    @Override
    protected List<String> toStringSequence(TimeTracker object) {
        List<String> stringSequence = new LinkedList<>();

        String unfinishedLogEntryString = getNestedConverters().get(0).convertToString(object.getUnfinishedLogEntry());
        String logString = getNestedConverters().get(1).convertToString(object.getLog());

        stringSequence.addAll(Arrays.asList(
                sanitize(object.getName()),
                sanitize(object.getTime()),
                sanitize(object.getTargetTime()),
                sanitize(object.isActive()),
                getGoDeeper() + unfinishedLogEntryString + getGoUp(),
                getGoDeeper() + logString + getGoUp()));

        return stringSequence;
    }

    @Override
    protected TimeTracker fromStringSequence(List<String> strings) {
        Log log = (Log) getNestedConverters().get(1).convertFromString(strings.get(strings.size() - 1));
        UnfinishedLogEntry unfinishedLogEntry = (UnfinishedLogEntry) getNestedConverters().get(0).convertFromString(strings.get(strings.size() - 2));

        return new TimeTracker(
                strings.get(0),
                Integer.parseInt(strings.get(1)),
                Integer.parseInt(strings.get(2)),
                log,
                unfinishedLogEntry);
    }
}
