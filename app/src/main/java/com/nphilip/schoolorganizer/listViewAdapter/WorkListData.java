package com.nphilip.schoolorganizer.listViewAdapter;

public class WorkListData {

    // Local Variables
    String work, selectedDate;
    int importance;
    boolean clickedStatus;

    /**
     * Constructor writes the parameters to local variables
     * @param work String
     * @param importance int
     * @param selectedDate String
     */
    public WorkListData(String work, int importance, String selectedDate, boolean clickedStatus) {
        this.work = work;
        this.importance = importance;
        this.selectedDate = selectedDate;
        this.clickedStatus = clickedStatus;
    }

    /**
     * Sets the work to "work"
     * @param work String
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * Sets the selectedDate to "selectedDate"
     * @param selectedDate String
     */
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * Sets the importance to "importance"
     * @param importance int
     */
    public void setImportance(int importance) {
        this.importance = importance;
    }

    /**
     * Sets the clicked status to "clickedStatus"
     * @param clickedStatus boolean
     */
    public void setClickedStatus(boolean clickedStatus) {
        this.clickedStatus = clickedStatus;
    }

    /**
     * Returns the work
     * @return String
     */
    public String getWork() {
        return work;
    }

    /**
     * Returns the time difference between now and selected date
     * @return String
     */
    public String getSelectedDate() {
        return selectedDate;
    }

    /**
     * Returns the importance
     * @return int
     */
    public int getImportance() {
        return importance;
    }

    /**
     * Returns the clicked status
     * @return boolean
     */
    public boolean getClickedStatus() {
        return clickedStatus;
    }
}
