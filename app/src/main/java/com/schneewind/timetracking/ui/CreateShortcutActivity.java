package com.schneewind.timetracking.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.schneewind.timetracking.R;

/**
 * An Activity that is shown when the user wants to create a new shortcut to a specific TimeTracker on the homescreen
 * @author Axel Schneewind
 * TODO: implement ListView and let user choose a TimerTracker that should be linked in the shortcut
 * TODO: add activity that is called when the shortcut is clicked, that enables/disables the given TimeTracker
 */
public class CreateShortcutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shortcut);

        Intent.ShortcutIconResource icon =
                Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher_foreground);

        Intent intent = new Intent();

        Intent launchIntent = new Intent(this,MainActivity.class);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut name");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        setResult(RESULT_OK, intent);

        finish();
    }
}