package com.schneewind.timetracking.timetracking.log;

/**
 * a class for log entries that have not finished yet
 * @author Axel Schneewind
 */
public class UnfinishedLogEntry {
    private boolean active;



    private long startingTime;
    private String info;

    /**
     * creates a new inactive unfinished log entry
     * @return
     */
    public static UnfinishedLogEntry create() {
        return new UnfinishedLogEntry();
    }


    /**
     * a function that creates an ongoing log entry, storing an info string and the starting time
     * @param info the info string
     * @param startingTime the time when the ongoing log entry began
     */
    public void start(String info, long startingTime){
        this.active = true;
        this.startingTime = startingTime;
    }

    /**
     * a function that terminates this OnGoingLogEntry and constructs a LogEntry from it
     * @param stoppingTime the time when this OnGoingLogEntry has been terminated
     * @return a LogEntry containing the info and starting time of the OnGoingLogEntry and the given stopping time
     */
    public LogEntry stop(long stoppingTime){
        this.active = false;
        return new LogEntry(this.startingTime, stoppingTime, info);
    }



    private UnfinishedLogEntry(String info, long startingTime){
        this.info = info;
        this.startingTime = startingTime;
    }

    private UnfinishedLogEntry(){
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public String getInfo() {
        return info;
    }
}
