package com.schneewind.timetracking;

import android.content.Context;

import java.io.BufferedReader;
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads a string from the default file
     * @return the string from the file
     */
    public String readFromDefaultFile(){
        String string = new String();

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }




    public FileHelper(Context context){
        this.context = context;
    }
}
