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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Restore the preferences
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        game_accuracy = settings.getInt("Game", 0);
        map_accuracy = settings.getInt("Map", 0);
        monitorYourChildren_accuracy = settings.getInt("MonitorYourChildren", 0);
        navigation_accuracy = settings.getInt("Navigation", 0);

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
        editor.putInt("Game", this.game_accuracy);
        editor.putInt("Map", this.map_accuracy);
        editor.putInt("MonitorYourChildren", this.monitorYourChildren_accuracy);
        editor.putInt("Navigation", this.navigation_accuracy);

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
                break;
            case 1:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(1).toString());
                settingsActivity.putExtra("selected_accuracy", this.map_accuracy);
                break;
            case 2:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(2).toString());
                settingsActivity.putExtra("selected_accuracy", this.monitorYourChildren_accuracy);
                break;
            case 3:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(3).toString());
                settingsActivity.putExtra("selected_accuracy", this.navigation_accuracy);
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
                } else if (data.getStringExtra("app_name").equals("Map")) {
                    this.map_accuracy = data.getIntExtra("accuracy", 0);
                } else if (data.getStringExtra("app_name").equals("MonitorYourChildren")) {
                    this.monitorYourChildren_accuracy = data.getIntExtra("accuracy", 0);
                } else {
                    this.navigation_accuracy = data.getIntExtra("accuracy", 0);
                }
            }
        }
    }
}
