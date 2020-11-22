package com.schneewind.timetracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeTrackerListAdapter extends BaseAdapter {
    Context context;

    TimeTrackingData timeTrackingData;

    LayoutInflater layoutInflater;

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

        View view = layoutInflater.inflate(R.layout.timetracker_list_element, null);

        ((TextView)view.findViewById(R.id.timetracker_name)).setText(tracker.getName());
        ((TextView)view.findViewById(R.id.timetracker_time)).setText(tracker.formatTime());

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

        return view;
    }

    public TimeTrackerListAdapter(Context context, TimeTrackingData timeTrackingData){
        this.context = context;
        this.timeTrackingData = timeTrackingData;
        this.layoutInflater = LayoutInflater.from(context);
    }
}
