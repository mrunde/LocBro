package de.ifgi.locbro;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class StartActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String values[]= new String[] {"Map", "Navigation", "Game", "MonitorYourChildren"};

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
    protected void onListItemClick(ListView l, View w, int position, long id){
        /* Spot Check test
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item+ " selected", Toast.LENGTH_LONG).show();
        */

        //TODO: Create the activityClass for different apps.
        switch ( position){
            case 0:  Intent newActivity = new Intent(this, map.class);
                startActivity(newActivity);
                break;
            case 1:  Intent newActivity = new Intent(this, navigation.class);
                startActivity(newActivity);
                break;
            case 2:  Intent newActivity = new Intent(this, game.class);
                startActivity(newActivity);
                break;
            case 3:  Intent newActivity = new Intent(this, monitoryourchild.class);
                startActivity(newActivity);
                break;
            case 4:  Intent newActivity = new Intent(this, somethingelse.class);
                startActivity(newActivity);
                break;
        }
    }
}
