package de.ifgi.locbro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Activity to change the preferences for the selected application
 *
 * @author Marius Runde
 */
public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    /**
     * Selected accuracy for application
     */
    private int selectedAccuracy;

    /**
     * Spinner for the accuracy
     */
    private Spinner spinnerAccuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Setup the GUI
        setupGUI();
    }

    /**
     * Setup the GUI of the activity
     */
    private void setupGUI() {
        // Get the Intent information
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("app_name"));
        this.selectedAccuracy = intent.getIntExtra("selected_accuracy", 0);

        // Setup the Spinner for the accuracy
        this.spinnerAccuracy = (Spinner) findViewById(R.id.spinner_accuracy);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.accuracy_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccuracy.setAdapter(arrayAdapter);
        spinnerAccuracy.setSelection(this.selectedAccuracy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view.equals(this.spinnerAccuracy)) {
            this.selectedAccuracy = adapterView.getSelectedItemPosition();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }
}
