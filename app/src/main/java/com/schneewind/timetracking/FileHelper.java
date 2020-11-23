package com.schneewind.timetracking;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

    String defaultFileName = "timetrackingdata.txt";

    MainActivity mainActivity;

    /**
     * saves a string to the default file
     * @param string string to be saved
     */
    public void writeToDefaultFile(String string){
        try {
            FileOutputStream fileOutputStream = mainActivity.getApplicationContext().openFileOutput(defaultFileName, Context.MODE_PRIVATE);
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
        mainActivity.writeBytesToFile(path,fileName, string.getBytes());
    }

    /**
     * reads a string from the default file
     * @return the string from the file
     */
    public String readFromDefaultFile(){
        String string = "";
        try {
            FileInputStream fileInputStream = mainActivity.getApplicationContext().openFileInput(defaultFileName);
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

    public FileHelper(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
}
