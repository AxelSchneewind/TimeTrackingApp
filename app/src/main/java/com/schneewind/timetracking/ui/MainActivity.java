package com.schneewind.timetracking.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTracker;
import com.schneewind.timetracking.timetracking.log.Log;
import com.schneewind.timetracking.timetracking.log.UnfinishedLogEntry;

public class MainActivity extends TimeTrackingActivity {
    private final int NEW_TIME_TRACKER_REQUEST_CODE = 100;
    private final String NOTIFICATION_CHANNEL_ID = "123";
    private final int NOTIFICATION_ID = 155;

    private TimeTracker selectedTimeTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FIXME: make switch setAllTimeTrackersActive
                if(view.getId() == R.id.app_bar_switch_button)
                    getTimeTrackingData().setAllTimeTrackersActive(((Switch)view).isChecked());
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_secondary,new DetailViewFragment(), "DETAILVIEW").commit();


        //FIXME: clicks are not received this way
        findViewById(R.id.content_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableDetails();
            }
        });

        createNotificationChannel();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewTimeTrackerActivity.class);
            MainActivity.this.startActivityForResult(intent, NEW_TIME_TRACKER_REQUEST_CODE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        removeNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        int activeTrackers = getTimeTrackingData().getActiveTrackerCount();
        if(activeTrackers != 0){
            String content = String.format("%d " + (activeTrackers > 1 ? getString(R.string.notification_content_plural) : getString(R.string.notification_content_singular)), activeTrackers);
            launchNotification(content);
        }
    }

    @Override
    protected void onTimerTick() {
        super.onTimerTick();

        getTimeTrackingData().addTimeToAllActiveTrackers(1);
        if(getTimeTrackingData().listAdapter != null) {
            getTimeTrackingData().listAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_TIME_TRACKER_REQUEST_CODE && resultCode == RESULT_OK) {
            getTimeTrackingData().addTimeTracker(new TimeTracker(data.getStringExtra("TrackerName"), data.getIntExtra("TrackerInitialValue",0), data.getIntExtra("TrackerTarget",0), new Log(), UnfinishedLogEntry.create()));
            getTimeTrackingData().writeTrackersToDefaultFile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_export){
            getTimeTrackingData().exportTrackers();
            return true;
        }

        if (id == R.id.action_import){
            getTimeTrackingData().importTrackers();
            return true;
        }

        if(id == R.id.app_bar_switch_button){
            getTimeTrackingData().setAllTimeTrackersActive(((Switch)findViewById(R.id.app_bar_switch)).isChecked());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Enables a detailview by making the DetailViewUI layout visible and setting its displayedTimeTracker to the given TimeTracker
     * This operation also sets the selectedTimeTracker of this MainActivity to the given TimeTracker
     * @param timeTracker the TimeTracker that should be displayed
     */
    public void enableDetails(TimeTracker timeTracker){
        setSelectedTimeTracker(timeTracker);

        findViewById(R.id.fragment_secondary).setVisibility(View.VISIBLE);

        DetailViewFragment detailViewFragment = (DetailViewFragment) getSupportFragmentManager().findFragmentByTag("DETAILVIEW");
        detailViewFragment.setDisplayedTimeTracker(getSelectedTimeTracker());
    }
    /**
     * Enables the detailview without changing the selectedTimeTracker a.k.a displayedTimeTracker of the DetailViewUI
     */
    public void enableDetails(){ enableDetails(selectedTimeTracker); }

    /**
     * Disables the detailview by making the DetailViewUI layout invisible
      */
    public void disableDetails(){ findViewById(R.id.fragment_secondary).setVisibility(View.GONE); }

    /**
     * sets the selectedTimeTracker field of this class
     * @param selectedTimeTracker the TimeTracker that should be selected
     */
    public void setSelectedTimeTracker(TimeTracker selectedTimeTracker){ this.selectedTimeTracker = selectedTimeTracker; }

    /**
     * returns the currently selected TimeTracker of this MainActivity
     * @return
     */
    public TimeTracker getSelectedTimeTracker(){ return selectedTimeTracker;}


    /**
     * launches the notification for the app
     */
    private void launchNotification(String content){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getApplication().getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setContentText(content)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * removes a created notification for the app
     */
    private void removeNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * creates the Notification of the app, has to be called on creation
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TimeTracking NotificationChannel";
            String description = "Channel for the TimeTracking notification which is shown when the app is closed";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}