package de.ifgi.locbro;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Marius Runde
 */
public class StartActivity extends ListActivity {

    private static final int SETTINGS_REQUEST = 1;
    // Store the accuracies for the applications
    private int game_accuracy;
    private int map_accuracy;
    private int monitorYourChildren_accuracy;
    private int navigation_accuracy;
    // Store the notification information
    private boolean game_notification;
    private boolean map_notification;
    private boolean monitorYourChildren_notification;
    private boolean navigation_notification;
    // Store the time information for the applications
    private int game_hour;
    private int game_minute;
    private int map_hour;
    private int map_minute;
    private int monitorYourChildren_hour;
    private int monitorYourChildren_minute;
    private int navigation_hour;
    private int navigation_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Restore the preferences
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        game_accuracy = settings.getInt("Game_accuracy", 0);
        game_notification = settings.getBoolean("Game_notification", false);
        game_hour = settings.getInt("Game_hour", 0);
        game_minute = settings.getInt("Game_minute", 0);
        map_accuracy = settings.getInt("Map_accuracy", 0);
        map_notification = settings.getBoolean("Map_notification", false);
        map_hour = settings.getInt("Map_hour", 0);
        map_minute = settings.getInt("Map_minute", 0);
        monitorYourChildren_accuracy = settings.getInt("MonitorYourChildren_accuracy", 0);
        monitorYourChildren_notification = settings.getBoolean("MonitorYourChildren_notification", false);
        monitorYourChildren_hour = settings.getInt("MonitorYourChildren_hour", 0);
        monitorYourChildren_minute = settings.getInt("MonitorYourChildren_minute", 0);
        navigation_accuracy = settings.getInt("Navigation_accuracy", 0);
        navigation_notification = settings.getBoolean("Navigation_notification", false);
        navigation_hour = settings.getInt("Navigation_hour", 0);
        navigation_minute = settings.getInt("Navigation_minute", 0);

        // Use demo application names
        String values[] = new String[]{"Game", "Map", "MonitorYourChildren", "Navigation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Store the preferences
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Game_accuracy", this.game_accuracy);
        editor.putBoolean("Game_notification", this.game_notification);
        editor.putInt("Game_hour", this.game_hour);
        editor.putInt("Game_minute", this.game_minute);
        editor.putInt("Map_accuracy", this.map_accuracy);
        editor.putBoolean("Map_notification", this.map_notification);
        editor.putInt("Map_hour", this.map_hour);
        editor.putInt("Map_minute", this.map_minute);
        editor.putInt("MonitorYourChildren_accuracy", this.monitorYourChildren_accuracy);
        editor.putBoolean("MonitorYourChildren_notification", this.monitorYourChildren_notification);
        editor.putInt("MonitorYourChildren_hour", this.monitorYourChildren_hour);
        editor.putInt("MonitorYourChildren_minute", this.monitorYourChildren_minute);
        editor.putInt("Navigation_accuracy", this.navigation_accuracy);
        editor.putBoolean("Navigation_notification", this.navigation_notification);
        editor.putInt("Navigation_hour", this.navigation_hour);
        editor.putInt("Navigation_minute", this.navigation_minute);

        // Commit the edits
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_reset_preferences) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Start the SettingsActivity for the selected application
        Intent settingsActivity = new Intent(this, SettingsActivity.class);
        switch (position) {
            case 0:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(0).toString());
                settingsActivity.putExtra("selected_accuracy", this.game_accuracy);
                settingsActivity.putExtra("selected_notification", this.game_notification);
                settingsActivity.putExtra("selected_hour", this.game_hour);
                settingsActivity.putExtra("selected_minute", this.game_minute);
                break;
            case 1:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(1).toString());
                settingsActivity.putExtra("selected_accuracy", this.map_accuracy);
                settingsActivity.putExtra("selected_notification", this.map_notification);
                settingsActivity.putExtra("selected_hour", this.map_hour);
                settingsActivity.putExtra("selected_minute", this.map_minute);
                break;
            case 2:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(2).toString());
                settingsActivity.putExtra("selected_accuracy", this.monitorYourChildren_accuracy);
                settingsActivity.putExtra("selected_notification", this.monitorYourChildren_notification);
                settingsActivity.putExtra("selected_hour", this.monitorYourChildren_hour);
                settingsActivity.putExtra("selected_minute", this.monitorYourChildren_minute);
                break;
            case 3:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(3).toString());
                settingsActivity.putExtra("selected_accuracy", this.navigation_accuracy);
                settingsActivity.putExtra("selected_notification", this.navigation_notification);
                settingsActivity.putExtra("selected_hour", this.navigation_hour);
                settingsActivity.putExtra("selected_minute", this.navigation_minute);
                break;
        }
        // Start the SettingsActivity
        startActivityForResult(settingsActivity, this.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == this.SETTINGS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("app_name").equals("Game")) {
                    this.game_accuracy = data.getIntExtra("accuracy", 0);
                    this.game_notification = data.getBooleanExtra("notification", false);
                    this.game_hour = data.getIntExtra("hour", 0);
                    this.game_minute = data.getIntExtra("minute", 0);
                } else if (data.getStringExtra("app_name").equals("Map")) {
                    this.map_accuracy = data.getIntExtra("accuracy", 0);
                    this.map_notification = data.getBooleanExtra("notification", false);
                    this.map_hour = data.getIntExtra("hour", 0);
                    this.map_minute = data.getIntExtra("minute", 0);
                } else if (data.getStringExtra("app_name").equals("MonitorYourChildren")) {
                    this.monitorYourChildren_accuracy = data.getIntExtra("accuracy", 0);
                    this.monitorYourChildren_notification = data.getBooleanExtra("notification", false);
                    this.monitorYourChildren_hour = data.getIntExtra("hour", 0);
                    this.monitorYourChildren_minute = data.getIntExtra("minute", 0);
                } else {
                    this.navigation_accuracy = data.getIntExtra("accuracy", 0);
                    this.navigation_notification = data.getBooleanExtra("notification", false);
                    this.navigation_hour = data.getIntExtra("hour", 0);
                    this.navigation_minute = data.getIntExtra("minute", 0);
                }
            }
        }
    }
}
