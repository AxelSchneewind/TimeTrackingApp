package com.schneewind.timetracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewTimeTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_tracker);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.tracker_name)).getText().toString();
                int target = Integer.parseInt(((EditText)findViewById(R.id.tracker_target)).getText().toString());
                int initial = Integer.parseInt(((EditText)findViewById(R.id.tracker_initial_value)).getText().toString());


                TimeTracker tracker = new TimeTracker(name,initial * 3600, target * 3600);

                Intent i = new Intent(NewTimeTrackerActivity.this, MainActivity.class);
                NewTimeTrackerActivity.this.startActivity(i);
            }
        });
    }
}