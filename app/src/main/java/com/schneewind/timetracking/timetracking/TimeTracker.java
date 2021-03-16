package com.schneewind.timetracking.timetracking;


import com.schneewind.timetracking.timetracking.log.Log;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntry;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * A class for storing information of a TimeTracker
 * @author Axel Schneewind
 * */
public class TimeTracker {
    private final String name;
    private int time;
    private int targetTime;

    private boolean active;

    private final UnfinishedLogEntry unfinishedLogEntry;

    private final Log log;


    /**
     * getter for the name of the TimeTracker
     * @return the name of this TimeTracker
     */
    public String getName(){ return name; }

    /**
     * @return the amount of seconds the Tracker has been active
     */
    public int getTime(){
        return time;
    }

    /**
     * returns the target time
     * @return
     */
    public int getTargetTime(){ return targetTime; }

    /**
     * checks whether this tracker is active
     * @return
     */
    public boolean isActive(){ return active; }

    /**
     * getter for the current unfinished log entry of this TimeTracker
     * @return
     */
    public UnfinishedLogEntry getUnfinishedLogEntry() {
        return unfinishedLogEntry;
    }

    /**
     * sets the value of active, determining whether the tracker should count or not
     * if this TimeTracker gets disabled, a new LogEntry is added to its log
     * @param active the new value of active, if none is given active is negated
     */
    public void setActive(boolean active){
        if(active == isActive()){ return; }

        if(isActive()){
            log.addEntry(unfinishedLogEntry.stop(Date.from(Instant.now()).getTime()));
        } else {
            unfinishedLogEntry.start("", Date.from(Instant.now()).getTime());
        }

        this.active = active;
    }
    public void setActive(){ setActive(!this.isActive());}

    /**
     * sets the Time of the Tracker
     * @param time the amount of time (in seconds) to be stored in the tracker
     */
    public void setTime(int time){
        this.time = time;
    }

    /**
     * sets the target of the tracker
     * @param time time in seconds
     */
    public void setTargetTime(int time){ this.targetTime = time; }

    /**
     * Adds an amount of time to the Tracker
     * @param seconds the amount of time to add
     */
    public void addTime(int seconds){
        setTime(time + seconds);
    }


    //TODO move time formatting to separate class
    /**
     * Formats the given time and returns a String
     * @param formatType the FormatType determining what the String should look like
     * @param time the time in seconds to format
     * @return a formatted String
     */
    public static String formatTime(FormatType formatType, int time){
        int _secondsPassed = time;
        int hours  = 0;
        int minutes = 0;
        int seconds = 0;

        while(_secondsPassed >= 3600){
            _secondsPassed -= 3600;
            hours++;
        }
        while(_secondsPassed >= 60){
            _secondsPassed -= 60;
            minutes++;
        }
        seconds = _secondsPassed;

        String formattedTime = "";
        /* for string formatting, see https://www.baeldung.com/java-number-formatting */
        switch (formatType){
            case FULL:
                formattedTime = String.format("%0" + 2 + "d" + "h %0" + 2 + "d" + "m %0" + 2 + "d" +"s", hours, minutes, seconds);
                break;
            case HOUR:
                float timeShort = hours + (float)minutes/60;
                formattedTime = String.format("%.1fh",timeShort);
                break;
        }
        return formattedTime;
    }

    /**
     * formats the time stored in this TimeTracker
     * @param formatType the FormatType determining what the String should look like
     * @return a formatted String
     */
    public String formatTime(FormatType formatType){
        return formatTime(formatType, time);
    }

    /**
     * Formats the target time stored in this TimeTracker
     * @param formatType the FormatType determining what the String should look like
     * @return a formatted String
     */
    public String formatTargetTime(FormatType formatType) {
        return formatTime(formatType, targetTime);
    }


    /**
     * returns the log object of this time tracker
     * @return
     */
    public Log getLog() {
        return log;
    }


    /**
     * Default constructor of a TimeTracker
     * @param name the name of the TimeTracker
     * @param time the amount of time stored int the TimeTracker
     * @param targetTime the target amount of time of the TimeTracker
     * @param log the log of the TimeTracker
     */
    public TimeTracker(String name, int time, int targetTime, Log log, UnfinishedLogEntry unfinishedLogEntry){
        this.name = name;
        this.time = time;
        this.targetTime = targetTime;
        this.active = false;
        this.log = log;
        this.unfinishedLogEntry = unfinishedLogEntry;
    }


    @Override
    public int hashCode() {
        return (Arrays.hashCode(getName().toCharArray()) + getTime() + getTargetTime() + (isActive() ? 1 : 0)) * getLog().hashCode() * getUnfinishedLogEntry().hashCode() ;
    }

    public enum FormatType{
        HOUR,
        FULL
    }
}
