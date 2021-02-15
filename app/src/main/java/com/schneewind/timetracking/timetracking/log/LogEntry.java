package com.schneewind.timetracking.timetracking.log;

import java.util.Optional;

/*@
  @ public instance invariant startTime <= stopTime
  @*/
/**
 * A class storing information of a LogEntry, including a period of time, and an optional information string
 * LogEntries are immutable, for unfinished (i.e. ongoing) log entries
 * @author Axel Schneewind
 */
public class LogEntry {
    private final Optional<String> info;
    private final long startTime;
    private final long stopTime;

    /**
     * getter for the optional info string of this LogEntry
     * @return the optional string, returns empty string if there is no info stored
     */
    public String getInfo() {
        return info.orElse("");
    }

    /**
     * getter for the starting time of the time period of this entry
     * @return the starting time as a long (in milliseconds)
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * getter for the ending time of the time period of this entry
     * @return the termination time as a long (in milliseconds)
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * returns the duration of the time period of this log entry
     * @return the duration as a long (in milliseconds)
     */
    public long getDuration(){
        return stopTime - startTime;
    }


    /**
     * constructor for a log entry
     * @param info data to be stored on this log entry
     * @param startTime starting time of the time period stored in this entry
     * @param stopTime  termination time of the time period stored in this entry
     */
    public LogEntry(long startTime, long stopTime, String info) {
        if(startTime > stopTime){
            throw new IllegalArgumentException();
        }

        if(info.equals("")){
            this.info = Optional.empty();
        } else{
            this.info = Optional.of(info);
        }

        this.startTime = startTime;
        this.stopTime = stopTime;
    }
}
