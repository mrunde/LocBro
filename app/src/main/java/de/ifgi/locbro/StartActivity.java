package de.ifgi.locbro;

import android.app.ListActivity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String values[] = new String[]{"Map", "Navigation", "Game", "MonitorYourChildren"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View w, int position, long id) {
        // Start the SettingsActivity for the selected application
        Intent settingsActivity = new Intent(this, SettingsActivity.class);
        settingsActivity.putExtra("selected_accuracy", 1); // TODO
        switch (position) {
            case 0:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(0).toString());
                startActivity(settingsActivity);
                break;
            case 1:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(1).toString());
                startActivity(settingsActivity);
                break;
            case 2:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(2).toString());
                startActivity(settingsActivity);
                break;
            case 3:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(3).toString());
                startActivity(settingsActivity);
                break;
            case 4:
                settingsActivity.putExtra("app_name", this.getListAdapter().getItem(4).toString());
                startActivity(settingsActivity);
                break;
        }
    }
}
