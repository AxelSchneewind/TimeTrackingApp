package com.schneewind.timetracking;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public int NEWTIMETRACKER_REQUEST_CODE = 100;


    public TimeTrackingData timeTrackingData;


    public Thread TimerTick = new Thread(){
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                timeTrackingData.addTimeToAllTrackers(1);
                runOnUiThread(() -> timeTrackingData.listAdapter.notifyDataSetChanged());
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

        timeTrackingData = new TimeTrackingData(MainActivity.this);
        timeTrackingData.readTrackersFromDefaultFile();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewTimeTrackerActivity.class);
            MainActivity.this.startActivityForResult(intent, NEWTIMETRACKER_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEWTIMETRACKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String n = data.getStringExtra("TrackerName"); int init = data.getIntExtra("TrackerInitialValue", 1); int target = data.getIntExtra("TrackerTarget", 4);
            timeTrackingData.addTimeTracker(new TimeTracker(data.getStringExtra("TrackerName"), data.getIntExtra("TrackerInitialValue",0), data.getIntExtra("TrackerTarget",36000)));
            timeTrackingData.writeTrackersToDefaultFile();
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
            timeTrackingData.exportTrackers();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * writes set of a bytes to any file in devices storage
     * !Function cant handle invalid paths(no permission, etc.)
     * @param path target directory
     * @param filename name of the new file
     * @param bytes content of the file
     */
    public void writeBytesToFile(File path, String filename, byte[] bytes){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            return;
        }

        try {
            File file = new File(path, filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the content of a given file and returns an array of bytes
     * @param path target directory
     * @param filename name of the file
     * @return an array of bytes
     */
    public byte[] readBytesOfFile(File path, String filename){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            return null;
        }

        String string = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path, filename));
            BufferedReader  reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line + "\n");
                line = reader.readLine();
            }
            string = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.getBytes();
    }
}