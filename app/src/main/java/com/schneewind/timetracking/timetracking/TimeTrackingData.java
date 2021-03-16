package com.schneewind.timetracking.timetracking;

import android.os.Environment;

import com.schneewind.timetracking.FileHelper;
import com.schneewind.timetracking.ui.TimeTrackingActivity;
import com.schneewind.timetracking.ui.TimeTrackingListAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Axel Schneewind
 * a class for storing the instances of the TimeTrackers during runtime.
 * conatains methods for reading/writing TimeTrackers to files by using the FileHelper methods
 */
public class TimeTrackingData {
    //TODO review usage of this reference and remove it
    private TimeTrackingActivity timeTrackingActivity;

    private final ArrayList<TimeTracker> trackers = new ArrayList<>();

    private final String trackerSeparator = "\nnewtracker\n";


    private final TimeTrackerStringConverterOld trackerStringConverterOld = new TimeTrackerStringConverterOld('\n',';',',');
    private final TimeTrackerStringConverter trackerStringConverter = new TimeTrackerStringConverter();


    public TimeTrackingListAdapter listAdapter;

    public TimeTrackingData() {}

    /**
     * reads Trackers from the default file and stores them in this TimeTrackingData object
     * requires timeTrackingActivity of this TimeTrackingData instance to be assigned
     */
    public void readTrackersFromDefaultFile(){
        //TODO: implement functionality of reading files from older versions

        if (timeTrackingActivity == null) return;
        trackers.clear();

        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        String data = fileHelper.readFromDefaultFile(FileHelper.dataFile);
        String[] trackerStrings = data.split(trackerSeparator);

        for (String trackerString : trackerStrings) {
            if(!trackerString.equals("")){
                trackers.add(trackerStringConverter.convertFromString(trackerString));
            }
        }
    }

    /**
     * saves the Trackers from this TimeTrackingData object to the default file
     * requires timeTrackingActivity of this TimeTrackingData instance to be assigned
     */
    public void writeTrackersToDefaultFile(){
        if(timeTrackingActivity == null) return;

        String data = "";
        for (TimeTracker tracker : trackers) {
            data = data.concat(trackerStringConverter.convertToString(tracker) + trackerSeparator);
        }
        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        fileHelper.writeToDefaultFile(FileHelper.dataFile, data);
    }

    /**
     * creates FileHelper and uses its export function.
     * requires timeTrackingActivity of this TimeTrackingData instance to be assigned
     */
    public void exportTrackers(){
        if(timeTrackingActivity == null) return;
        String data = "";
        for (TimeTracker tracker : trackers) {
            data = data.concat(trackerStringConverter.convertToString(tracker) + trackerSeparator);
        }

        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        fileHelper.writeToExternalFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FileHelper.exportFile, data);
    }

    /**
     * creates FileHelper and uses its import function. require timeTrackingActivity of thie TimeTrackingData instance to be assigned
     * TODO: implement file picker to choose the file to import
     */
    public void importTrackers(){
        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        String data = fileHelper.readFromExternalFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FileHelper.exportFile);
        String[] trackerStrings = data.split(trackerSeparator);

        for (String trackerString : trackerStrings) {
            if(!trackerString.equals("")) trackers.add(trackerStringConverter.convertFromString(trackerString));
        }
    }


    //TODO make session file save and read unfinished log entries
    /**
     * Generating a file that stores data of the current session containing: current time, active TimeTrackers.
     * requires timeTrackingActivity of this TimeTrackingData instance to be assigned
     */
    public void saveSessionData(){
        if(timeTrackingActivity == null) return;

        String data = "";
        data = data.concat(Calendar.getInstance().getTimeInMillis() + "\n");

        for (int i = 0; i < trackers.size(); i++) {
            TimeTracker tracker = trackers.get(i);
            if (tracker.isActive()) data = data.concat(i + ";" );
        }
        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        fileHelper.writeToDefaultFile(FileHelper.sessionFile, data);
    }

    /**
     * reads a given String containing session data and applies it to the stored TimeTrackers (active status, last time of activity).
     * requires the timeTrackingActivity of this TimeTrackingData instance to be assigned.
     * needs to be called after calling readTrackersFromDefaultFile().
     */
    public void readSessionData(){
        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        String data = fileHelper.readFromDefaultFile(FileHelper.sessionFile);

        if(!data.contains("\n") || !data.contains(";")) return;

        String[] segments = data.split("\n");

        String[] activeTrackerIndices = segments[1].split(";");
        setAllTimeTrackersActive(false);
        for (String activeTrackerIndex : activeTrackerIndices) {
            getTimeTracker(Integer.parseInt(activeTrackerIndex)).setActive(true);
        }

        long timeDelta = (Calendar.getInstance().getTimeInMillis() - Long.parseLong(segments[0])) / 1000;
        addTimeToAllActiveTrackers((int) timeDelta);

        clearSessionData();
    }

    /**
     * clears the sessionData file, needs to be called after reading the data of the sessionData file to prevent repeatedly applying it.
     * requires the timeTrackingActivity of this TimeTrackingData instance to be assigned
     */
    public void clearSessionData(){
        if(timeTrackingActivity == null) throw new IllegalStateException();
        FileHelper fileHelper = new FileHelper(timeTrackingActivity);
        String data = "";
        fileHelper.writeToDefaultFile(FileHelper.sessionFile, data);
    }

    /**
     * adds a given amount of time to all active trackers
     * @param time time in seconds
     */
    public void addTimeToAllActiveTrackers(int time){
        for (TimeTracker tracker : trackers) {
            if(tracker.isActive()) tracker.addTime(time);
        }
    }

    /**
     * Activates/Deactivates all stored TimeTrackers
     * @param active whether all TimeTrackers should be active or not
     */
    public void setAllTimeTrackersActive(boolean active){
        for (TimeTracker tracker : trackers) {
            tracker.setActive(active);
        }
    }

    /**
     * Adds a new TimeTracker
     * @param newTracker the TimeTracker to be added
     */
    public void addTimeTracker(TimeTracker newTracker){
        trackers.add(newTracker);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * removes a given TimeTracker from the list
     * @param tracker the tracker to delete (use getTimeTracker() or findTimeTracker() if necessary)
     */
    public void removeTimeTracker(TimeTracker tracker){
        trackers.remove(tracker);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * Returns the timetracker at the given index in the list
     * @param index index of the timetracker in the list
     * @return the TimeTracker object at the given index
     */
    public TimeTracker getTimeTracker(int index){
        return trackers.get(index);
    }

    /**
     * searches the stored TimeTrackers and returns the index of the given TimeTracker
     * @param timeTracker the timeTracker to search for
     * @return the index of the TimeTracker as an int
     */
    public int getTimeTrackerIndex(TimeTracker timeTracker){
        for (int i = 0; i < trackers.size(); i++) {
            if(timeTracker.equals(trackers.get(i))) return i;
        }
        throw new IllegalArgumentException();
    }

    /**
     * finds and returns the timetracker of a given name
     * @param name the name of the tracker
     * @return the TimeTracker with the given name, returns null if no such tracker exists
     */
    public TimeTracker findTimeTracker(String name){
        for (TimeTracker tracker : trackers) {
            if(tracker.getName().equals(name)) return tracker;
        }
        return null;
    }

    /**
     * returns the number of trackers that are stored
     * @return an integer
     */
    public int getTrackerCount(){
        return trackers.size();
    }

    /**
     * returns the number of trackers that are active
     * @return an integer
     */
    public int getActiveTrackerCount(){
        int count = 0;
        for (TimeTracker tracker : trackers) {
            if(tracker.isActive()) count++;
        }
        return count;
    }

    /**
     * sets the given TimeTrackingActivity as this TimeTrackingDatas timeTrackingActivity.
     * !needs to be called at least once before any files can be written/read!
     * @param timeTrackingActivity any activity of the timeTrackingActivity type. Typically the MainActivity
     */
    public void setTimeTrackingActivity(TimeTrackingActivity timeTrackingActivity){
        this.timeTrackingActivity = timeTrackingActivity;
    }
}
