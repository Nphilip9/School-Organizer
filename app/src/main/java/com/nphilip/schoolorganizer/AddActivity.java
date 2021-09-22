package com.nphilip.schoolorganizer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nphilip.schoolorganizer.listViewAdapter.WorkListData;
import com.nphilip.schoolorganizer.manager.CheckboxManager;
import com.nphilip.schoolorganizer.manager.ItemManager;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class AddActivity extends AppCompatActivity {

    // View Components
    EditText addActivity_editText_workType;
    SeekBar addActivity_seekBar_importance;
    TextView addActivity_textView_importanceInfoText, addActivity_textView_selectedImportance,
            addActivity_textView_selectedDate, addActivity_textView_timeLeft;
    Button addActivity_button_datePicker, addActivity_button_add;

    // Reference to ItemManager, CheckboxManager
    ItemManager itemManager = new ItemManager();
    CheckboxManager checkboxManager = new CheckboxManager();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        // initialize View Components
        addActivity_editText_workType = findViewById(R.id.addActivity_editText_workType);
        addActivity_seekBar_importance = findViewById(R.id.addActivity_seekBar_importance);
        addActivity_textView_importanceInfoText = findViewById(R.id.addActivity_textView_importanceInfoText);
        addActivity_textView_selectedImportance = findViewById(R.id.addActivity_textView_selectedImportance);
        addActivity_textView_timeLeft = findViewById(R.id.addActivity_textView_timeLeft);
        addActivity_textView_selectedDate = findViewById(R.id.addActivity_textView_selectedDate);
        addActivity_button_datePicker = findViewById(R.id.addActivity_button_datePicker);
        addActivity_button_add = findViewById(R.id.addActivity_button_add);

        // View Components configurations
        addActivity_textView_selectedImportance.setText(getApplicationContext().getResources().getString(R.string.strings_string_selectedImportance) + " " + 0);

        // Creating String variable for selected date. Lambda expression doesn't allow to declare variable
        // from outside the expression and initializes inside the expression
        AtomicReference<String> selectedDate = new AtomicReference<>();

        // Updates the displayed importance in the TextView "addActivity_seekBar_importance"
        // default importance is 0
        addActivity_seekBar_importance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean bool) {
                addActivity_textView_selectedImportance.setText(getApplicationContext().getResources().getString(R.string.strings_string_selectedImportance) + " " + value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ignore
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ignore
            }
        });

        // OnClick on "addActivity_button_datePicker" calls the datePicker dialog
        // Date difference from today to selected date gets displayed to "addActivity_button_datePicker"
        addActivity_button_datePicker.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, (datePicker, year, month, day) -> {
                selectedDate.set(day + "." + month + "." + year);
                String timeDifference = itemManager.getTimeDifference(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." +
                        Calendar.getInstance().get(Calendar.MONTH) + "." + Calendar.getInstance().get(Calendar.YEAR), day + "." + month + "." + year);
                addActivity_textView_timeLeft.setText(timeDifference);
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // OnClick on "addActivity_button_add" calls the startMainActivity()
        addActivity_button_add.setOnClickListener(v -> {
            try {
                itemManager.addItemToWorkList(getFilesDir() + "/WorkList.txt", new WorkListData(addActivity_editText_workType.getText().toString(), addActivity_seekBar_importance.getProgress(), selectedDate.get()));
                checkboxManager.addNewCheckboxStatus(getFilesDir() + "/Checkboxes.txt", false);
                startMainActivity();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // On Back Button Pressed call startMainActivity() and finish current Activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMainActivity();
        finish();
    }

    // Starts the MainActivity Class
    public void startMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
