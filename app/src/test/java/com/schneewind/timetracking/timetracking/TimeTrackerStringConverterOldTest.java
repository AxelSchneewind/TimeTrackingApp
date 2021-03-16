package com.schneewind.timetracking.timetracking;

import com.schneewind.timetracking.timetracking.log.Log;
import com.schneewind.timetracking.timetracking.log.LogEntry;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TimeTrackerStringConverterOldTest {

    TimeTracker timeTracker;
    TimeTrackerStringConverterOld converter;


    @Before
    public void setUp() throws Exception {
        Random random = new Random();


        Log log = new Log();
        int entryCount = random.nextInt(128);

        for(int i = 0; i < entryCount; i++){
            long time = random.nextLong();

            log.addEntry(new LogEntry(time, (long)random.nextInt(1000) + time,  randomString(200)));
        }

        int targetTime = random.nextInt(10000);

        timeTracker = new TimeTracker(randomString(120), targetTime - random.nextInt(100000),targetTime, log, UnfinishedLogEntry.create());

        converter = new TimeTrackerStringConverterOld(',',';','~');
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void convertToString() {
        String converted = converter.convertToString(timeTracker);
        System.out.println("String of the timeTracker: " + converted);

        TimeTracker reconverted = converter.convertFromString(converted);

        assertEquals(timeTracker.getName(), reconverted.getName());
        assertEquals(timeTracker.getTime(), reconverted.getTime());
        assertEquals(timeTracker.getTargetTime(), reconverted.getTargetTime());
        assertEquals(timeTracker.getLog().hashCode(), reconverted.getLog().hashCode());
        assertEquals(timeTracker.getUnfinishedLogEntry().hashCode(), reconverted.getUnfinishedLogEntry().hashCode());

        assertEquals(timeTracker.hashCode(),reconverted.hashCode());
    }

    @Test
    public void convertFromString() {
    }

    String randomString(int maxLength){
        Random random = new Random();

        int length = random.nextInt(maxLength);
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < length; j++){
            result.append((char)(random.nextInt(64) + 64));
        }

        return result.toString();
    }
}