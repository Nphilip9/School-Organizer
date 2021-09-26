package com.nphilip.schoolorganizer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nphilip.schoolorganizer.manager.ItemManager;

import java.io.IOException;
import java.util.Calendar;

public class ItemDetailsActivity extends AppCompatActivity {

    // View Components
    TextView itemDetailsActivity_textView_work, itemDetailsActivity_textView_importance,
            itemDetailsActivity_textView_date;
    Button itemDetailsActivity_button_modifyWork, itemDetailsActivity_button_modifyImportance,
            itemDetailsActivity_button_modifyDate, itemDetailsActivity_button_apply;
    FloatingActionButton itemDetailsActivity_fab_deleteItem;

    // Reference to ItemManager
    ItemManager itemManager = new ItemManager();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activity);

        // Initialize View Components
        itemDetailsActivity_textView_work = findViewById(R.id.itemDetailsActivity_textView_work);
        itemDetailsActivity_textView_importance = findViewById(R.id.itemDetailsActivity_textView_importance);
        itemDetailsActivity_textView_date = findViewById(R.id.itemDetailsActivity_textView_date);
        itemDetailsActivity_button_modifyWork = findViewById(R.id.itemDetailsActivity_button_modifyWork);
        itemDetailsActivity_button_modifyImportance = findViewById(R.id.itemDetailsActivity_button_modifyImportance);
        itemDetailsActivity_button_modifyDate = findViewById(R.id.itemDetailsActivity_button_modifyDate);
        itemDetailsActivity_button_apply = findViewById(R.id.itemDetailsActivity_button_apply);
        itemDetailsActivity_fab_deleteItem = findViewById(R.id.itemDetailsActivity_fab_deleteItem);

        // configure view components
        try {
            System.out.println(itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt"));
            itemDetailsActivity_textView_work.append(" " + itemManager.getItemsFromWorkList(getFilesDir() + "/WorkList.txt").get(itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt")).getWork());
            itemDetailsActivity_textView_importance.append(" " + itemManager.getItemsFromWorkList(getFilesDir() + "/WorkList.txt").get(itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt")).getImportance());
            itemDetailsActivity_textView_date.append(" " + itemManager.getItemsFromWorkList(getFilesDir() + "/WorkList.txt").get(itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt")).getSelectedDate());
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemDetailsActivity_button_modifyWork.setOnClickListener(v -> {
            // Create an alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsActivity.this);
            builder.setTitle("Modify Work");

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_work, null);
            builder.setView(customLayout);

            EditText newValue = customLayout.findViewById(R.id.customAlertDialog_editText_newValue);
            newValue.setText(itemDetailsActivity_textView_work.getText().toString().replace("Work: ", ""));
            newValue.setSelectAllOnFocus(true);

            // Add Positive and Negative Button
            builder.setPositiveButton("OK", (dialog, which) -> itemDetailsActivity_textView_work.setText(getApplicationContext().getResources().getString(R.string.strings_string_work) + " " + newValue.getText().toString()));
            builder.setNegativeButton("Cancel", (dialog, i) -> { /* Action canceled by user. Do nothing */ });

            // create and show
            // the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        itemDetailsActivity_button_modifyImportance.setOnClickListener(v -> {
            // Create an alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailsActivity.this);
            builder.setTitle("Modify Importance");

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_importance, null);
            builder.setView(customLayout);

            SeekBar newValue = customLayout.findViewById(R.id.customAlertDialog_seekBar_importance);

            // Add Positive and Negative Button
            builder.setPositiveButton("OK", (dialog, which) -> itemDetailsActivity_textView_importance.setText(getApplicationContext().getResources().getString(R.string.strings_string_importance) + " " + newValue.getProgress()));
            builder.setNegativeButton("Cancel", (dialog, i) -> { /* Action canceled by user. Do nothing */ });

            // create and show
            // the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        itemDetailsActivity_button_modifyDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ItemDetailsActivity.this, (datePicker, year, month, day) -> {
                String selectedDate = day + "." + month + "." + year;
                itemDetailsActivity_textView_date.setText(selectedDate);
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        itemDetailsActivity_button_apply.setOnClickListener(view -> {
            try {
                itemManager.modifyItem(getFilesDir() + "/WorkList.txt",
                        itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt"),
                        ItemManager.CHANGE_ITEM_WORK, itemDetailsActivity_textView_work.getText().toString().replace("Work: ", ""));

                itemManager.modifyItem(getFilesDir() + "/WorkList.txt",
                        itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt"),
                        ItemManager.CHANGE_ITEM_IMPORTANCE, itemDetailsActivity_textView_importance.getText().toString().replace("Importance: ", ""));

                itemManager.modifyItem(getFilesDir() + "/WorkList.txt",
                        itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt"),
                        ItemManager.CHANGE_ITEM_SELECTED_DATE, itemDetailsActivity_textView_date.getText().toString().replace("Date: ", ""));

                startMainActivity();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        itemDetailsActivity_fab_deleteItem.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemDetailsActivity.this);
            alertDialogBuilder.setTitle("Are you sure?");

            alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
                try {
                    itemManager.deleteItemFromWorkList(getFilesDir() + "/WorkList.txt", itemManager.getClickedListViewItemID(getCacheDir() + "/ClickedListViewItem.txt"));
                    startMainActivity();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> { /* Action canceled by user. Do nothing */ });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMainActivity();
        finish();
    }

    // Starts the Main Activity
    public void startMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
