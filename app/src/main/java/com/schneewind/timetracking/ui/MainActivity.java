package com.schneewind.timetracking.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTracker;
import com.schneewind.timetracking.timetracking.TimeTrackingActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends TimeTrackingActivity {
    public int NEWTIMETRACKER_REQUEST_CODE = 100;
    public String NOTIFICATION_CHANNEL_ID = "123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNotificationChannel();
        launchNotification();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewTimeTrackerActivity.class);
            MainActivity.this.startActivityForResult(intent, NEWTIMETRACKER_REQUEST_CODE);
        });
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

        if(requestCode == NEWTIMETRACKER_REQUEST_CODE && resultCode == RESULT_OK) {
            getTimeTrackingData().addTimeTracker(new TimeTracker(data.getStringExtra("TrackerName"), data.getIntExtra("TrackerInitialValue",0), data.getIntExtra("TrackerTarget",36000)));
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

        return super.onOptionsItemSelected(item);
    }

    private void launchNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("TimeTracker")
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(0, builder.build());
    }

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