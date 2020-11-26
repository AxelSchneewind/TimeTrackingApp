package com.schneewind.timetracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTracker;

public class DetailViewFragment extends Fragment {

    private TimeTracker displayedTimeTracker;

    private TextView trackerNameTextView;
    private TextView trackerTimeTextView;
    private TextView trackerTargetTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailview, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trackerNameTextView = (TextView)getActivity().findViewById(R.id.detailview_trackername);
        trackerTimeTextView = (TextView) getActivity().findViewById(R.id.detailview_trackertime);
        trackerTargetTextView = (TextView) getActivity().findViewById(R.id.detailview_trackertarget);

    }

    /**
     * Sets the TimeTracker to display and calls the UpdateUI function
     * @param displayedTimeTracker reference to the TimeTracker to display
     */
    public void setDisplayedTimeTracker(TimeTracker displayedTimeTracker){
        this.displayedTimeTracker = displayedTimeTracker;
        UpdateUI();
    }

    /**
     * Updates the UI elements of this fragment to match the data of the displayedTimeTracker
     */
    public void UpdateUI(){
        trackerNameTextView.setText(displayedTimeTracker.getName());
        trackerTimeTextView.setText(displayedTimeTracker.formatTime(TimeTracker.FormatType.FULL));
        trackerTargetTextView.setText(displayedTimeTracker.formatTargetTime(TimeTracker.FormatType.FULL));
    }


    public DetailViewFragment(TimeTracker timeTracker){
        displayedTimeTracker = timeTracker;
    }
    public DetailViewFragment(){}
}