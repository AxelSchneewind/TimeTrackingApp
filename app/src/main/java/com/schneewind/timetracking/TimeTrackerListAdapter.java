package com.schneewind.timetracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimeTrackerListAdapter extends BaseAdapter {
    Context context;

    TimeTrackingData timeTrackingData;

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
            view = layoutInflater.inflate(R.layout.timetracker_list_element, null);
            listElementViews.add(view);
        }else{
            view = listElementViews.get(position);
        }

        if(tracker.getTargetTime() != 0) {
            int progress = ((ProgressBar)view.findViewById(R.id.timetracker_progress)).getMax() * tracker.getTime() / tracker.getTargetTime();
            ((ProgressBar)view.findViewById(R.id.timetracker_progress)).setProgress(progress);
        } else{
            view.findViewById(R.id.timetracker_progress).setVisibility(View.GONE);
        }

        ((TextView)view.findViewById(R.id.timetracker_name)).setText(tracker.getName());
        ((TextView)view.findViewById(R.id.timetracker_time)).setText(tracker.formatTime(TimeTracker.FormatType.HOUR));

        ImageButton activeButton = view.findViewById(R.id.timetracker_active);
        if(tracker.isActive())
            activeButton.setImageResource(R.drawable.ic_pause_button);
        else
            activeButton.setImageResource(R.drawable.ic_play_button);

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

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                timeTrackingData.removeTimeTracker((TimeTracker)getItem(position));
                return false;
            }
        });

        return view;
    }

    public TimeTrackerListAdapter(Context context, TimeTrackingData timeTrackingData){
        this.context = context;
        this.timeTrackingData = timeTrackingData;
        this.layoutInflater = LayoutInflater.from(context);
    }
}
