package com.schneewind.timetracking.timetracking;


/**
 * @author Axel Schneewind
 * A class for storing information of a TimeTracker
 */
public class TimeTracker {

    private final String name;
    private int time;
    private int targetTime;

    private boolean active;

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
     * sets the value of active, determining whether the tracker should count or not
     * @param active the new value of active, if none is given active is negated
     */
    public void setActive(boolean active){ this.active = active; }
    public void setActive(){ this.active = !this.active;}

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
     * returns a string that can be saved in a text file
     * @return A string containing name and time of a TimeTracker
     */
    public String toSaveableString(){
        String formattedString = String.format("%s;%d;%d",name, time,targetTime);
        return formattedString;
    }

    /**
     * Constructs a TimeTracker from a saved String
     * @param savedString a string that has been read from a file
     * @return a TimeTracker with the data from the string
     */
    public static TimeTracker fromSavedString(String savedString){
        String[] segments = savedString.split(";");

        return new TimeTracker(segments[0], Integer.parseInt(segments[1]), Integer.parseInt(segments[2]));
    }

    public TimeTracker(String name, int time, int targetTime){
        this.name = name;
        this.time = time;
        this.targetTime = targetTime;
        this.active = false;
    }



    public enum FormatType{
        HOUR,
        FULL
    }
}
