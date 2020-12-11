package com.schneewind.timetracking.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTracker;
import com.schneewind.timetracking.timetracking.TimeTrackingData;

import java.util.ArrayList;

public class TimeTrackingListAdapter extends BaseAdapter {
    Context context;

    TimeTrackingData timeTrackingData;
    ArrayList<UpdateableUI> observingFragments = new ArrayList<>();

    LayoutInflater layoutInflater;

    ArrayList<View> listElementViews = new ArrayList<View>();

    @Override
    public int getCount() {
        return timeTrackingData.getTrackerCount();
    }

    @Override
    public Object getItem(int position) {
        return timeTrackingData.getTimeTracker(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeTracker tracker =  timeTrackingData.getTimeTracker(position);

        View view;
        if(listElementViews.size() <= position){
            //Initialize the view
            view = layoutInflater.inflate(R.layout.timetracker_list_element, null);

            ImageButton activeButton = view.findViewById(R.id.timetracker_active);
            if(tracker.isActive()) {
                activeButton.setImageResource(R.drawable.ic_pause_button);
            }else{
                activeButton.setImageResource(R.drawable.ic_play_button);
            }
            activeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TimeTracker)getItem(position)).setActive();

                    if(tracker.isActive())
                        activeButton.setImageResource(R.drawable.ic_pause_button);
                    else
                        activeButton.setImageResource(R.drawable.ic_play_button);
                }
            });

            if(tracker.getTargetTime() == 0) {
                view.findViewById(R.id.timetracker_progress).setVisibility(View.GONE);
            }

            ((TextView)view.findViewById(R.id.timetracker_name)).setText(tracker.getName());

            listElementViews.add(view);
        }else{
            view = listElementViews.get(position);
        }

        if(tracker.getTargetTime() != 0) {
            int progress = ((ProgressBar)view.findViewById(R.id.timetracker_progress)).getMax() * tracker.getTime() / tracker.getTargetTime();
            ((ProgressBar)view.findViewById(R.id.timetracker_progress)).setProgress(progress);
        }

        ((TextView)view.findViewById(R.id.timetracker_time)).setText(tracker.formatTime(TimeTracker.FormatType.FULL));

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (UpdateableUI ui: observingFragments) {
            try {ui.UpdateUI();} catch (NullPointerException e){ e.printStackTrace(); }
        }
    }

    public TimeTrackingListAdapter(Context context, TimeTrackingData timeTrackingData){
        this.context = context;
        this.timeTrackingData = timeTrackingData;
        this.layoutInflater = LayoutInflater.from(context);
    }
}
