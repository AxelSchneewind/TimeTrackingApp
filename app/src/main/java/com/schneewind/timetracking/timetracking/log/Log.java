package com.schneewind.timetracking.timetracking.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*@
  @ public instance invariant logEntries != null
  @ public instance invariant \foreach LogEntry entry1, entry2 from logEntries : entry1.getStartingTime() > entry2.getStartingTime() <==> logEntries.indexOf(entry1) > logEntries.indexOf(entry2)
  @*/
/**
 * A class for storing log entries, sorted by their starting time
 * @author Axel Schneewind
 */
public class Log {
    private final List<LogEntry> logEntries;



    /**
     * adds a given LogEntry to this Log
     * @param logEntry the entry to add
     */
    public void addEntry(LogEntry logEntry){
        logEntries.add(logEntry);
        logEntries.sort(comparator);
    }

    /**
     * removes a given entry from this log
     * @param entry the entry to remove
     */
    public void removeEntry(LogEntry entry){
        if(!logEntries.contains(entry)){
            throw new IllegalArgumentException("There is no such entry in this log");
        }
        logEntries.remove(entry);
        logEntries.sort(comparator);
    }

    /**
     * gets the most entry with the most recent starting time
     * @return
     */
    public LogEntry getLatestEntry(){
        return logEntries.get(logEntries.size() - 1);
    }

    /**
     * getter for the list of log entries in this log
     * @return
     */
    public List<LogEntry> getLogEntries(){
        return logEntries;
    }


    /**
     * default constructor of a log containing the given logEntriees
     * @param logEntries
     */
    public Log(Collection<LogEntry> logEntries) {
        List<LogEntry> entries = new ArrayList<LogEntry>(logEntries);
        entries.sort(comparator);
        this.logEntries = entries;
    }

    /**
     * constructor for an empty log
     */
    public Log(){
        this.logEntries = new ArrayList<LogEntry>();
    }

    /**
     * A comparator for comparing logEntries based on their starting time
     */
    private final Comparator<LogEntry> comparator = new Comparator<LogEntry>() {
        @Override
        public int compare(LogEntry entry1, LogEntry entry2) {
            return (int)(entry1.getStartTime() - entry2.getStartTime());
        }
    };

    @Override
    public int hashCode() {
        int hash = 0;
        for (LogEntry entry : logEntries) {
            hash += entry.getInfo().hashCode() + entry.getStartTime() + entry.getStopTime();
        }
        return hash;
    }
}


