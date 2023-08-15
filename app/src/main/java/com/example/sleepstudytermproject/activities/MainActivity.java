package com.example.sleepstudytermproject.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sleepstudytermproject.R;
import com.example.sleepstudytermproject.lib.Utils;
import com.example.sleepstudytermproject.models.SleepStudy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.sleepstudytermproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText selectedAge;
    private final String BEDTIME_TITLE = "Bedtime", WAKEUP_TITLE = "Wake Up Time";

    private SleepStudy sleepStudy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the toolbar as the app's action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedAge = findViewById(R.id.ageEditText);

        if(savedInstanceState != null) {
            String gson = savedInstanceState.getString("key");
            sleepStudy = SleepStudy.getObjectFromJSONString(gson);
        } else {
            sleepStudy = new SleepStudy();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", sleepStudy.getJSONStringFromThis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            showSettings();
            return true;
        } else if (id == R.id.action_about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Utils.showInfoDialog(MainActivity.this, getString(R.string.about_title), getString(R.string.about_text));
    }

    private void showSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void selectWakeUpTime(View view) {
        showTimePicker(WAKEUP_TITLE);
    }

    public void selectBedtime(View view) {
        showTimePicker(BEDTIME_TITLE);
    }

    public void calculateSleepDuration(View view) {
        sleepStudy.setAge(Integer.parseInt(selectedAge.getText().toString()));

        String recommendation = sleepStudy.getCalcResult();

        // Show Snackbar with result of sleep study
        Snackbar.make(binding.getRoot(), recommendation, Snackbar.LENGTH_INDEFINITE).show();
    }




    private void showTimePicker(String title) {
        MaterialTimePicker timePicker = createMaterialTimePicker(title);
        timePicker.show(getSupportFragmentManager(), title);
    }

    private MaterialTimePicker createMaterialTimePicker(String title) {
        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder();
        //builder.setHour(23);
       //use title to  know which timepicker is being set
        /*vif timealreadyset
                then builder.setHour use existing time
                also builder.setMinute
                uses constants of wakeup and bedtime

                */
        builder.setTimeFormat(TimeFormat.CLOCK_12H);
        builder.setPositiveButtonText("Set");
        builder.setTitleText(title);

        MaterialTimePicker timePicker = builder.build();
        timePicker.addOnPositiveButtonClickListener(v -> handleTimePickerClick(timePicker, title));

        return timePicker;
    }

    private void handleTimePickerClick(MaterialTimePicker timePicker, String title) {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        if (title.equals(WAKEUP_TITLE)) {
            sleepStudy.setWakeUpHour(hour);
            sleepStudy.setWakeUpMinute(minute);
        } else if (title.equals(BEDTIME_TITLE)) {
            sleepStudy.setBedTimeHour(hour);
            sleepStudy.setBedTimeMinute(minute);
        }
    }
}
