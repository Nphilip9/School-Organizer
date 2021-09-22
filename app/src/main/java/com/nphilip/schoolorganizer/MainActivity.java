package com.nphilip.schoolorganizer;import static com.nphilip.schoolorganizer.notifications.AppNotificationManager.CHANNEL_ID;import androidx.appcompat.app.AppCompatActivity;import androidx.core.app.NotificationCompat;import androidx.core.app.NotificationManagerCompat;import android.app.Notification;import android.content.Intent;import android.graphics.Paint;import android.os.Bundle;import android.util.Log;import android.widget.CheckedTextView;import android.widget.ListView;import com.google.android.material.floatingactionbutton.FloatingActionButton;import com.nphilip.schoolorganizer.listViewAdapter.CustomListViewAdapter;import com.nphilip.schoolorganizer.listViewAdapter.WorkListData;import com.nphilip.schoolorganizer.manager.CheckboxManager;import com.nphilip.schoolorganizer.manager.ItemManager;import java.io.File;import java.io.IOException;import java.util.ArrayList;import java.util.Arrays;public class MainActivity extends AppCompatActivity {    // View Components    FloatingActionButton mainActivity_fab_add;    ListView mainActivity_listView_workList;    // "workListDataArrayList" stores workList    ArrayList<WorkListData> workListDataArrayList = new ArrayList<>();    // Reference to CheckboxManager, ItemManager    CheckboxManager checkboxManager = new CheckboxManager();    ItemManager itemManager = new ItemManager();    // Classes for the Notification    NotificationManagerCompat notificationManagerCompat;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        // Initializing View Components        mainActivity_fab_add = findViewById(R.id.mainActivity_fab_add);        mainActivity_listView_workList = findViewById(R.id.mainActivity_listView_workList);        // First call of update() initializes the listView and displays the data to the screen        try {            update();        } catch (IOException e) {            e.printStackTrace();        }        // Setting choice mode on "mainActivity_listView_workList"        // Choice mode allows to click/change the check-boolean-variable and the checkMark        // Without setting the choice mode on "mainActivity_listView_workList" it could only clicked one time        mainActivity_listView_workList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);        // Initialize notificationManagerCompat        notificationManagerCompat = NotificationManagerCompat.from(this);        // FloatingActionButton "mainActivity_fab_add" calls onClickListener startAddActivity()        // the finish() function stops the MainActivity Class        mainActivity_fab_add.setOnClickListener(v -> {            startAddActivity();            finish();        });        // OnCLick on "mainActivity_listView_workList" change the clicked ListView Item selected status to the opposite,        // change text flag, change the checkMarkIcon and change the boolean value in "Checkboxes.txt" files to the opposite        mainActivity_listView_workList.setOnItemClickListener((adapterView, view, i, l) -> {            CheckedTextView checkedTextView = (CheckedTextView) view;            if (!checkedTextView.isChecked()) {                try {                    checkboxManager.changeCheckboxValue(getFilesDir() + "/Checkboxes.txt", i, false);                } catch (IOException e) {                    e.printStackTrace();                }                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);                mainActivity_listView_workList.setItemChecked(i, false);                checkedTextView.setPaintFlags(checkedTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));                Log.d("List View Item " + i, "Set selected: " + false);            } else {                try {                    checkboxManager.changeCheckboxValue(getFilesDir() + "/Checkboxes.txt", i, true);                } catch (IOException e) {                    e.printStackTrace();                }                checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);                mainActivity_listView_workList.setItemChecked(i, true);                checkedTextView.setPaintFlags(checkedTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);                Log.d("List View Item " + i, "Set selected: " + true);            }        });        // OnLongClick "mainActivity_listView_workList" call startItemDetailsActivity() and set the clickedListView Item ID to        // the last clicked ListView Item ID. Change this ID in the "ClickedListViewItem.txt" file        mainActivity_listView_workList.setOnItemLongClickListener((adapterView, view, i, l) -> {            startItemDetailsActivity();            try {                itemManager.setClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt", String.valueOf(i));            } catch (IOException e) {                e.printStackTrace();            }            finish();            return false;        });    }    // Reinitialize the listView "mainActivity_listView_workList" every single time update() is called    public void update() throws IOException {        if(new File(getFilesDir() + "/WorkList.txt").exists()) {            workListDataArrayList = itemManager.getItemsFromWorkList(getFilesDir() + "/WorkList.txt");        }        // Write the ArrayList<WorkListData> "workListDataArrayList" to a normal list        String[][] workListData = new String[workListDataArrayList.size()][3];        for (int i = 0; i < workListDataArrayList.size(); i++) {            workListData[i][0] = workListDataArrayList.get(i).getWork();            workListData[i][1] = String.valueOf(workListDataArrayList.get(i).getImportance());            workListData[i][2] = workListDataArrayList.get(i).getSelectedDate();        }        Arrays.sort(workListData, (first, second) -> {            // Sorts the Array by the importance level            // highest (0) ... lowest (5)            return Double.valueOf(second[1]).compareTo(Double.valueOf(first[1])            );        });        // Write the sorted list to an ArrayList<WorkListData>        ArrayList<WorkListData> finalWorkListDataList = new ArrayList<>();        for (String[] workListDataElement : workListData) {            finalWorkListDataList.add(new WorkListData(workListDataElement[0], Integer.parseInt(workListDataElement[1]), workListDataElement[2]));        }        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(MainActivity.this, finalWorkListDataList, mainActivity_listView_workList, getFilesDir() + "/Checkboxes.txt", getFilesDir() + "/WorkList.txt");        mainActivity_listView_workList.setAdapter(customListViewAdapter);    }    /**     * Sends a notification to the user when time is less then 24 hours     * @param notificationContentTitle String     * @param notificationContentText String     */    private void sendNotification(String notificationContentTitle, String notificationContentText) {        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)                .setSmallIcon(R.drawable.ic_baseline_access_time_24)                .setContentTitle("Tomorrow is deadline")                .setContentText("Important")                .setPriority(NotificationCompat.PRIORITY_MAX)                .setCategory(NotificationCompat.CATEGORY_REMINDER).build();        notificationManagerCompat.notify(1, notification);    }    @Override    protected void onResume() {        super.onResume();        try {            update();        } catch (IOException e) {            e.printStackTrace();        }    }    // Starts the AddActivity Class    public void startAddActivity() {        Intent addActivityIntent = new Intent(this, AddActivity.class);        startActivity(addActivityIntent);    }    // Starts the ItemDetailsActivity Class    public void startItemDetailsActivity() {        Intent itemDetailsActivity = new Intent(this, ItemDetailsActivity.class);        startActivity(itemDetailsActivity);    }}