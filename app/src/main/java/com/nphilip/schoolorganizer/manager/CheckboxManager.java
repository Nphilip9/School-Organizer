package com.nphilip.schoolorganizer.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckboxManager {

    /**
     * Deletes a checkbox value from the "Checkboxes.txt" file
     * @param path String
     * @param position int
     * @throws IOException RuntimeException
     */
    public void deleteCheckboxValue(String path, int position) throws IOException {
        // File content is stored in "booleanArrayList"
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets added to the arraylist
            booleanArrayList.add(Boolean.parseBoolean(strLine));
        }

        // Removing the boolean value from position "position"
        booleanArrayList.remove(position);
        if(new File(path).delete()) {
            for (boolean bool : booleanArrayList) {
                addNewCheckboxStatus(path, bool);
            }
        }

        // Close after opening/reading/writing
        fileInputStream.close();
    }

    /**
     * Changes a single boolean value in the "Checkboxes.txt" file
     * @param path path
     * @param position int
     * @param value boolean
     * @throws IOException RuntimeException
     */
    public void changeCheckboxValue(String path, int position, boolean value) throws IOException {
        // File content is stored in "booleanArrayList"
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets added to the arraylist
            booleanArrayList.add(Boolean.parseBoolean(strLine));
        }

        // ArraysList "booleanArrayList" gets written to a normal list of boolean for easier changes
        boolean[] booleans = new boolean[booleanArrayList.size()];

        for (int i = 0; i < booleanArrayList.size(); i++) {
            booleans[i] = booleanArrayList.get(i);
        }

        booleans[position] = value;

        if(new File(path).delete()) {
            for (boolean bool : booleans) {
                addNewCheckboxStatus(path, bool);
            }
        }

        // Close after opening/reading/writing
        fileInputStream.close();
    }

    /**
     * Returns the saved boolean values from the checkboxes. Return empty list if file is empty
     * @param path String
     * @return boolean[]
     * @throws IOException RuntimeException
     */
    public boolean[] getCheckboxStatuses(String path) throws IOException {
        // File content is stored in "booleanArrayList"
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets added to the arraylist
            booleanArrayList.add(Boolean.parseBoolean(strLine));
        }

        // ArraysList "booleanArrayList" gets written to a normal List because the return condition is [] and not ArrayList
        boolean[] booleans = new boolean[booleanArrayList.size()];

        for (int i = 0; i < booleanArrayList.size(); i++) {
            booleans[i] = booleanArrayList.get(i);
        }

        // Close after opening/reading
        fileInputStream.close();

        return booleans;
    }

    /**
     * Adds a new boolean "value" on creating a new work element
     * @param path String
     * @param value boolean
     * @throws IOException RuntimeException
     */
    public void addNewCheckboxStatus(String path, boolean value) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        fileWriter.append(String.valueOf(value)).append("\n");
        fileWriter.close();
    }
}
