package com.schneewind.timetracking.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.schneewind.timetracking.TimeTrackingApp;
import com.schneewind.timetracking.timetracking.TimeTrackingData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Axel Schneewind
 *
 * An activity class that has functionality like FileIO and TimerTick calls
 */
public class TimeTrackingActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTimeTrackingData().setTimeTrackingActivity(this);
        getTimeTrackingData().readTrackersFromDefaultFile();

        TimerTick.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getTimeTrackingData().readSessionData();
        boolean b = TimerTick.isAlive(); boolean a = TimerTick.isInterrupted();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getTimeTrackingData().saveSessionData();
        TimerTick.interrupt();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getTimeTrackingData().saveSessionData();
        getTimeTrackingData().writeTrackersToDefaultFile();
    }

    public Thread TimerTick = new Thread(){
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onTimerTick();
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected void onTimerTick(){ }

    /**
     * gets the Apps timeTrackingData Object
     * @return the TimeTrackingData instance of the App
     */
    public TimeTrackingData getTimeTrackingData(){
        return ((TimeTrackingApp)getApplication()).timeTrackingData;
    }

    /**
     * writes set of a bytes to any file in devices storage
     * !Function cant handle invalid paths(no permission, etc.)
     * @param path target directory
     * @param filename name of the new file
     * @param bytes content of the file
     */
    public void writeBytesToFile(File path, String filename, byte[] bytes){
        if(ContextCompat.checkSelfPermission(TimeTrackingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TimeTrackingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
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
        if(ContextCompat.checkSelfPermission(TimeTrackingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TimeTrackingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            return null;
        }

        String string = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path, filename));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            string = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.getBytes();
    }
}
