package com.schneewind.timetracking.timetracking;

public class TimeTracker {

    private String name;
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
     * @return a formatted string of the stored time (hours minutes seconds)
     */
    public String formatTime(FormatType formatType){
        int _secondsPassed = time;
        int hours  = 0;
        int minutes = 0;
        int seconds = 0;

        while(_secondsPassed > 3600){
            _secondsPassed -= 3600;
            hours++;
        }
        while(_secondsPassed > 60){
            _secondsPassed -= 60;
            minutes++;
        }
        seconds = _secondsPassed;

        String formattedTime = "";
        switch (formatType){
            case FULL:
                formattedTime = String.format("%2dh %2dm %2ds", hours, minutes, seconds);
                break;
            case HOUR:
                float time = hours + (float)minutes/60;
                formattedTime = String.format("%.1fh",time);
                break;
        }


        return formattedTime;
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

        return new TimeTracker(segments[0], Integer.valueOf(segments[1]), Integer.valueOf(segments[2]));
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
