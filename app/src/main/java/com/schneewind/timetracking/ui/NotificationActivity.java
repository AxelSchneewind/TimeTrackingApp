package com.schneewind.timetracking.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTracker;
import com.schneewind.timetracking.timetracking.TimeTrackingActivity;
import com.schneewind.timetracking.timetracking.TimeTrackingData;

/**
 * TODO
 * associate this activity with the notification
 */

public class NotificationActivity extends TimeTrackingActivity {

    public TimeTrackingData timeTrackingData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        ((TextView)findViewById(R.id.trackerTime)).setText(timeTrackingData.getTimeTracker(0).formatTime(TimeTracker.FormatType.FULL));
    }

    @Override
    protected void onTimerTick() {
        super.onTimerTick();
    }
}
