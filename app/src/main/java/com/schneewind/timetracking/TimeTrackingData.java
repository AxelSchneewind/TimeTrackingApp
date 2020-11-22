package com.schneewind.timetracking;

import android.content.Context;

import java.util.ArrayList;

public class TimeTrackingData {
    Context context;

    ArrayList<TimeTracker> trackers = new ArrayList<TimeTracker>();

    public TimeTrackerListAdapter listAdapter;

    /**
     * reads Trackers from the default file and stores them in this TimeTrackingData object
     */
    public void readTrackersFromDefaultFile(){
        FileHelper fileHelper = new FileHelper(context);
        String data = fileHelper.readFromDefaultFile();
        String[] trackerStrings = data.split("\n");

        for (String trackerString : trackerStrings) {
            if(trackerString != "") trackers.add(TimeTracker.fromSavedString(trackerString));
        }
    }

    /**
     * saves the Trackers from this TimeTrackingData object to the default file
     */
    public void writeTrackersToDefaultFile(){
        String data = new String();
        for (TimeTracker tracker : trackers) {
            data = data.concat(tracker.toSaveableString() + "\n");
        }
        FileHelper fileHelper = new FileHelper(context);
        fileHelper.writeToDefaultFile(data);
    }

    /**
     * adds a given amount of time to all active trackers
     * @param time
     */
    public void addTimeToAllTrackers(int time){
        for (TimeTracker tracker : trackers) {
            if(tracker.isActive()) tracker.addTime(time);
        }
    }

    /**
     * Adds a new TimeTracker
     * @param newTracker the TimeTracker to be added
     */
    public void addTimeTracker(TimeTracker newTracker){
        trackers.add(newTracker);
    }

    /**
     * Returns the timetracker at the given index in the list
     * @param index
     * @return
     */
    public TimeTracker getTimeTracker(int index){
        return trackers.get(index);
    }

    /**
     * finds the timetracker of a given name
     * @param name the name of the tracker
     * @return the TimeTracker with the given name, returns null if no such tracker exists
     */
    public TimeTracker findTimeTracker(String name){
        for (TimeTracker tracker : trackers) {
            if(tracker.getName() == name) return tracker;
        }
        return null;
    }

    public int getTrackerCount(){ return trackers.size(); }

    public TimeTrackingData(Context context){
        this.context = context;
    }
}
