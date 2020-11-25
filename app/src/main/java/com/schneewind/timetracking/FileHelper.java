package com.schneewind.timetracking;

import android.content.Context;

import com.schneewind.timetracking.timetracking.TimeTrackingActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Axel Schneewind
 * A class for creating file from String data and vice versa
 */
public class FileHelper {
    public static String dataFile = "timetracking.txt", sessionFile = "session.data";

    TimeTrackingActivity timeTrackingActivity;

    /**
     * saves a string to the default file
     * @param string string to be saved
     */
    public void writeToDefaultFile(String dataFile,String string){
        try {
            FileOutputStream fileOutputStream = timeTrackingActivity.getApplicationContext().openFileOutput(dataFile, Context.MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());
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
    public void writeToExternalFile(File path, String fileName, String string){
        timeTrackingActivity.writeBytesToFile(path,fileName, string.getBytes());
    }

    /**
     * reads a string from the default file
     * @return the string from the file
     */
    public String readFromDefaultFile(String dataFile){
        String string = "";
        try {
            FileInputStream fileInputStream = timeTrackingActivity.getApplicationContext().openFileInput(dataFile);
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

    /**
     * calls the readBytesOfFile method of the TimeTrackingActivity and returns it as a string
     * @param path
     * @param fileName
     * @return the string generated from the bytes via standard encoding
     */
    public String readFromExternalFile(File path, String fileName){
        byte[] bytes = timeTrackingActivity.readBytesOfFile(path,fileName);
        String string = new String(bytes);
        return string;
    }

    public FileHelper(TimeTrackingActivity timeTrackingActivity){
        this.timeTrackingActivity = timeTrackingActivity;
    }
}
