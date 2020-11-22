package com.schneewind.timetracking;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

    String defaultFileName = "timetrackingdata.txt";

    Context context;

    /**
     * saves a string to the default file
     * @param string string to be saved
     */
    public void writeToDefaultFile(String string){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(defaultFileName, Context.MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes a string into a file at a specified path
     * @param path the directory of the new file
     * @param string the data to be stored
     */
    public void writeToExternalFile(String path, String string){
        File file = new File(path, defaultFileName);

        if(ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context.getApplicationContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }

        try {
            if(!file.exists()) file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads a string from the default file
     * @return the string from the file
     */
    public String readFromDefaultFile(){
        String string = "";
        try {
            FileInputStream fileInputStream = context.openFileInput(defaultFileName);
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

        return string;
    }


    public FileHelper(Context context){
        this.context = context;
    }
}
