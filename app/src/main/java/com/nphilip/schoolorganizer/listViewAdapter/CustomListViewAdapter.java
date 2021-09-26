package com.nphilip.schoolorganizer.listViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.nphilip.schoolorganizer.R;
import com.nphilip.schoolorganizer.manager.ItemManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomListViewAdapter extends BaseAdapter {

    // Size of colored border lines
    public static final int STROKE_WIDTH = 5;

    Context context;
    ArrayList<WorkListData> workListDataArrayList;
    CheckedTextView workListItem_textView_listViewItem;
    ListView workList;
    String checkboxFilePath, workListFilePath;

    /**
     * Constructor writes the parameters to local variables
     * @param context Context
     * @param workListDataArrayList ArraysList<WorkListData>
     * @param workList ListView
     * @param checkboxFilePath String
     * @param workListFilePath String
     */
    public CustomListViewAdapter(Context context, ArrayList<WorkListData> workListDataArrayList, ListView workList, String checkboxFilePath, String workListFilePath) {
        this.context = context;
        this.checkboxFilePath = checkboxFilePath;
        this.workList = workList;
        this.workListDataArrayList = workListDataArrayList;
        this.workListFilePath = workListFilePath;
    }

    /**
     * Implemented method from BaseAdapter
     * Return the count of the Items
     * @return int
     */
    @Override
    public int getCount() {
        return workListDataArrayList.size();
    }

    /**
     * Implemented method from BaseAdapter
     * Return the position of the Items
     * @param position int
     * @return Object
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * Implemented method from BaseAdapter
     * Returns the item Id of the Items
     * @param position int
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Implemented method from BaseAdapter
     * Sets the text of the Items that are displayed on the activity_main
     * @param position int
     * @param view View
     * @param viewGroup ViewGroup
     * @return View
     */
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_view_item, viewGroup, false);
        workListItem_textView_listViewItem = view.findViewById(R.id.listViewItem_textView_ListViewItem);
        ItemManager itemManager = new ItemManager();

        try {
            boolean clicked = itemManager.getItemsFromWorkList(workListFilePath).get(position).getClickedStatus();
            if (clicked) {
                itemManager.modifyItem(workListFilePath, position, ItemManager.CHANGE_ITEM_CLICKED_STATUS, "true");
                workList.setItemChecked(position, true);
                workListItem_textView_listViewItem.setPaintFlags(workListItem_textView_listViewItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                workListItem_textView_listViewItem.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                Log.d("List View Item " + position, "Set selected: " + true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        workListItem_textView_listViewItem.setText("Work: " + workListDataArrayList.get(position).getWork() + "\n" +
                "Importance: " + workListDataArrayList.get(position).getImportance() + "\n" +
                "Time Left: " + itemManager.getTimeDifference(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." +
                Calendar.getInstance().get(Calendar.MONTH) + "." + Calendar.getInstance().get(Calendar.YEAR), workListDataArrayList.get(position).getSelectedDate()));

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(7);

        switch (workListDataArrayList.get(position).getImportance()) {
            case 0:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#90ee90"));
                break;
            case 1:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#ffff00"));
                break;
            case 2:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#ffa500"));
                break;
            case 3:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#ff8c00"));
                break;
            case 4:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#ff474c"));
                break;
            case 5:
                gradientDrawable.setStroke(STROKE_WIDTH, Color.parseColor("#720000"));
                break;
        }

        workListItem_textView_listViewItem.setBackground(gradientDrawable);

        return view;
    }
}
