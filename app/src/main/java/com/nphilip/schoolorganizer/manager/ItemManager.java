package com.nphilip.schoolorganizer.manager;

import android.annotation.SuppressLint;

import com.nphilip.schoolorganizer.listViewAdapter.WorkListData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ItemManager {

    // Modify Type's
    public static final int CHANGE_ITEM_WORK = 1;
    public static final int CHANGE_ITEM_IMPORTANCE = 2;
    public static final int CHANGE_ITEM_SELECTED_DATE = 3;

    /**
     * Changes specific value from WorkListData element on "position"
     * Rewrites the file with the new modified Values
     * @param path String
     * @param position int
     * @param modifyType int
     * @param newValue String
     * @throws IOException RuntimeException
     */
    public void modifyItem(String path, int position, int modifyType, String newValue) throws IOException {
        // File content is stored in "workListDataArrayList"
        ArrayList<WorkListData> workListDataArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets split
            String[] lineSplit = strLine.split("##");

            // "lineSplit" elements gets added to list
            workListDataArrayList.add(new WorkListData(lineSplit[0], Integer.parseInt(lineSplit[1]), lineSplit[2]));
        }

        // Switch case on int var "modifyType"
        // on "CHANGE_ITEM_WORK" change old work-value to new String-value "newValue"
        // on "CHANGE_ITEM_IMPORTANCE" change old importance-value to new int-value "Integer.parseInt(newValue)"
        // on "CHANGE_ITEM_SELECTED_DATE"change old String-value to new String-value "new Value"
        // Change these new Values permanently
        switch (modifyType) {
            case CHANGE_ITEM_WORK:
                workListDataArrayList.get(position).setWork(newValue);
                fileInputStream.close();
                break;
            case CHANGE_ITEM_IMPORTANCE:
                workListDataArrayList.get(position).setImportance(Integer.parseInt(newValue));
                fileInputStream.close();
                break;
            case CHANGE_ITEM_SELECTED_DATE:
                workListDataArrayList.get(position).setSelectedDate(newValue);
                fileInputStream.close();
                break;
            default:
                fileInputStream.close();
                break;
        }

        // Write new Values to "WorkList.txt" file
        if(new File(path).delete()) {
            for (WorkListData workListData : workListDataArrayList) {
                addItemToWorkList(path, new WorkListData(workListData.getWork(), workListData.getImportance(), workListData.getSelectedDate()));
            }
        }

        // Closes and saves the file
        fileInputStream.close();
    }

    /**
     * Calculates the time difference between "today" and "future"
     * @param today String
     * @param future String
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public String getTimeDifference(String today, String future) {
        // format the strings today and future for the SimpleDateFormat
        today = today.replace(".", "/") + " " + new SimpleDateFormat("HH:mm:ss").format(new Date());
        future = future.replace(".", "/") + " 00:00:00";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date d1;
        Date d2;

        try {
            d1 = format.parse(today);
            d2 = format.parse(future);

            // Difference in milliseconds
            long diff = Objects.requireNonNull(d2).getTime() - Objects.requireNonNull(d1).getTime();

            // Calculate difference in Days, Hours, Minutes and Seconds
            long diffDays = diff / (24 * 60 * 60 * 1000);
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffSeconds = diff / 1000 % 60;

            return diffDays + "d " + diffHours + "h " + diffMinutes + "m " + diffSeconds + "s";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes item on "position" from "WorkList.txt" file and deletes checkbox Item
     * @param workFilePath String
     * @param checkboxFilePath String
     * @param clickedListViewItemFilePath String
     * @param position int
     * @throws IOException RuntimeException
     */
    public void deleteItemFromWorkList(String workFilePath, String checkboxFilePath, String clickedListViewItemFilePath, int position) throws IOException {
        // File content is stored in "workListDataArrayList"
        ArrayList<WorkListData> workListDataArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(workFilePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets split
            String[] lineSplit = strLine.split("##");

            // "lineSplit" elements gets added to list
            workListDataArrayList.add(new WorkListData(lineSplit[0], Integer.parseInt(lineSplit[1]), lineSplit[2]));
        }

        // Delete WorkListData-Element in "workListDataArrayList" on position "position"
        workListDataArrayList.remove(position);

        if(new File(workFilePath).delete()) {
            for (WorkListData workListData : workListDataArrayList) {
                addItemToWorkList(workFilePath, new WorkListData(workListData.getWork(), workListData.getImportance(), workListData.getSelectedDate()));
            }
        }

        // Closes and saves the file
        fileInputStream.close();

        CheckboxManager checkboxManager = new CheckboxManager();
        checkboxManager.deleteCheckboxValue(checkboxFilePath, getClickedListViewItemID(clickedListViewItemFilePath));
    }

    /**
     * Returns the WorkList in WorkListData form
     * @param path String
     * @return WorkListData[]
     * @throws IOException RuntimeException
     */
    public ArrayList<WorkListData> getItemsFromWorkList(String path) throws IOException {
        // File content is stored in "workListDataArrayList"
        ArrayList<WorkListData> workListDataArrayList = new ArrayList<>();

        // Opens/Loads the file from "path"
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        // Each line of file gets written to this variable
        String strLine;

        // Loop through lines until the last line
        while ((strLine = bufferedReader.readLine()) != null) {
            // "strLine" gets split
            String[] lineSplit = strLine.split("##");

            // "lineSplit" elements gets added to list
            workListDataArrayList.add(new WorkListData(lineSplit[0], Integer.parseInt(lineSplit[1]), lineSplit[2]));
        }

        // Closes and saves the file
        fileInputStream.close();

        return workListDataArrayList;
    }

    /**
     * Adds a new WorkListData "workListData" to WorkList
     * @param path String
     * @param workListData WorkListData
     * @throws IOException RuntimeException
     */
    public void addItemToWorkList(String path, WorkListData workListData) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        fileWriter.append(workListData.getWork()).append("##").append(String.valueOf(workListData.getImportance())).append("##")
                .append(workListData.getSelectedDate()).append("\n");
        fileWriter.close();
    }

    /**
     * Sets the clicked item from the listView in MainActivity
     * @param path String
     * @param position String
     * @throws IOException RuntimeException
     */
    public void setClickedListViewItemID(String path, String position) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(position);
        fileWriter.close();
    }

    /**
     * Returns the clicked item from the listView in MainActivity
     * @param path String
     * @return int
     * @throws IOException RuntimeException
     */
    public int getClickedListViewItemID(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        int clickedListViewItem = Integer.parseInt(bufferedReader.readLine());
        fileInputStream.close();
        return clickedListViewItem;
    }
}
