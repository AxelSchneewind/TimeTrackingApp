package com.schneewind.timetracking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.schneewind.timetracking.R;

public class NewTimeTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_tracker);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.tracker_name)).getText().toString();
                String _target = ((EditText)findViewById(R.id.tracker_target)).getText().toString();
                String _initial = ((EditText)findViewById(R.id.tracker_initial_value)).getText().toString();

                if(_target.equals("")) _target = "0";
                if(_initial.equals("")) _initial = "0";
                int target = Integer.parseInt(_target);
                int initial = Integer.parseInt(_initial);

                Intent returnTrackerIntent = new Intent();
                returnTrackerIntent.putExtra("TrackerName", name);
                returnTrackerIntent.putExtra("TrackerTarget", target * 3600);
                returnTrackerIntent.putExtra("TrackerInitialValue", initial * 3600);
                setResult(RESULT_OK, returnTrackerIntent);
                finish();
            }
        });
    }
}