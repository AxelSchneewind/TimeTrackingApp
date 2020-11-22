package com.schneewind.timetracking;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public TimeTrackingData timeTrackingData;


    public Thread TimerTick = new Thread(){
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                timeTrackingData.addTimeToAllTrackers(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTrackingData.listAdapter.notifyDataSetChanged();
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(getApplicationContext(), "updatethread interrupted, return to application", Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        if(!TimerTick.isAlive() || TimerTick.isInterrupted()) TimerTick.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeTrackingData.writeTrackersToDefaultFile();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timeTrackingData = new TimeTrackingData(getApplicationContext());
        timeTrackingData.readTrackersFromDefaultFile();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTimeTrackerActivity.class);
                MainActivity.this.startActivity(intent);

                timeTrackingData.addTimeTracker(new TimeTracker("Test" + timeTrackingData.getTrackerCount()));
                timeTrackingData.writeTrackersToDefaultFile();

                Snackbar.make(view, "Added a new TimeTracker", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}