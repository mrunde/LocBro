package de.ifgi.locbro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to manage the rules of the selected application
 *
 * @author Marius Runde
 */
public class RulesActivity extends Activity {


    public final static int NEW_RULE_REQUEST = 1;

    /**
     * List of rules
     */
    private List<Rule> rules = new ArrayList<Rule>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rules);
        // Get the Intent information
        Intent intent = getIntent();
        String app_name = intent.getStringExtra("app_name");
        setTitle(getResources().getString(R.string.title_activity_rules) + " " + app_name);

        /*
        // Restore the preferences
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.prefs_name), 0);
        boolean goOn = true;
        int counter = 1;
        while (goOn) {
            String ruleAsString = settings.getString(app_name + "_rule_" + counter, null);
            if (ruleAsString != null) {
                rules.add(Rule.createRuleFromString(ruleAsString));
            }
        }


        String values[] = new String[]{"Map", "Navigation", "Game", "MonitorYourChildren"}; // TODO

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        */

    }

    /**
     * Open Add Rule Activity
     * @param view
     */
    public void openAddRuleActivity(View view){
       Intent ruleActivity = new Intent(this, RuleActivity.class);

       startActivityForResult(ruleActivity, this.NEW_RULE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add_rule) {
            Intent ruleActivity = new Intent(this, RuleActivity.class);
            ruleActivity.putExtra("new_rule", true);
            startActivityForResult(ruleActivity, NEW_RULE_REQUEST);
            return true;
        } else if (id == R.id.menu_delete_all_rules) {
            rules.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == this.NEW_RULE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the result
                int start_hour = data.getIntExtra("start_hour", 0);
                int start_minute = data.getIntExtra("start_minute", 0);
                int end_hour = data.getIntExtra("end_hour", 0);
                int end_minute = data.getIntExtra("end_minute", 0);
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);

                // Create the new rule
                rules.add(new Rule(start_hour, start_minute, end_hour, end_minute, lat, lng));
            }
        }
    }
}
