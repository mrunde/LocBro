package de.ifgi.locbro;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * @author Marius Runde
 */
public class RuleActivity extends Activity implements View.OnClickListener {

    /**
     * Create new rule or edit existing one
     */
    private boolean isNew;

    // Time and geo fence information
    private int start_hour;
    private int start_minute;
    private int end_hour;
    private int end_minute;
    private double lat;
    private double lng;

    // GUI elements
    private Spinner spinner_accuracy;
    private ToggleButton tb_area;
    private ToggleButton tb_time;
    private Button btn_cancel;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        // Get Intent information
        Intent intent = getIntent();
        this.isNew = intent.getBooleanExtra("new_rule", true);

        setupGUI();
    }

    /**
     * Setup the GUI
     */
    private void setupGUI() {
        // 'Accuracy' spinner
        this.spinner_accuracy = (Spinner) findViewById(R.id.spinner_accuracy);

        // 'Area' toggle button
        this.tb_area = (ToggleButton) findViewById(R.id.tb_area);
        tb_area.setOnClickListener(this);

        // 'Time' toggle button
        this.tb_time = (ToggleButton) findViewById(R.id.tb_time);
        tb_time.setOnClickListener(this);

        // 'Cancel' button
        this.btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        // 'Save' button
        this.btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rule, menu);
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
    public void onBackPressed() {
        Intent resultIntent = new Intent(this, SettingsActivity.class);
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.equals(findViewById(R.id.tb_area))) {
            if (this.tb_area.isChecked()) {
                // TODO store area
            } else {
                this.start_hour = -1;
                this.start_minute = -1;
                this.end_hour = -1;
                this.end_minute = -1;
            }
        } else if (view.equals(findViewById(R.id.tb_time))) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);
            new TimePickerDialog(this, null, hour, minute, true);
        } else if (view.equals(findViewById(R.id.btn_cancel))) {
            Intent resultIntent = new Intent(this, SettingsActivity.class);
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        } else if (view.equals(findViewById(R.id.btn_save))) {
            Intent resultIntent = new Intent(this, SettingsActivity.class);
            resultIntent.putExtra("new_rule", this.isNew);
            resultIntent.putExtra("start_hour", this.start_hour);
            resultIntent.putExtra("start_minute", this.start_minute);
            resultIntent.putExtra("end_hour", this.end_hour);
            resultIntent.putExtra("end_minute", this.end_minute);
            resultIntent.putExtra("lat", this.lat);
            resultIntent.putExtra("lng", this.lng);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
