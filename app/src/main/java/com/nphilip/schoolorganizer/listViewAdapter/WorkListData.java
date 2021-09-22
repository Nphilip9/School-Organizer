package com.nphilip.schoolorganizer.listViewAdapter;

public class WorkListData {

    // Local Variables
    String work, selectedDate;
    int importance;

    /**
     * Constructor writes the parameters to local variables
     * @param work String
     * @param importance int
     * @param selectedDate String
     */
    public WorkListData(String work, int importance, String selectedDate) {
        this.work = work;
        this.importance = importance;
        this.selectedDate = selectedDate;
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
     * Return the importance
     * @return int
     */
    public int getImportance() {
        return importance;
    }
}
