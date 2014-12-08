package de.ifgi.locbro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;


public class TimePickerActivity extends Activity {

    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
    private Button btnSave;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        startTimePicker = (TimePicker) findViewById(R.id.timePicker_start);
        endTimePicker = (TimePicker) findViewById(R.id.timePicker_end);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        btnSave = (Button) findViewById(R.id.btn_time_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start_hour = TimePickerActivity.this.startTimePicker.getCurrentHour();
                int start_minute = TimePickerActivity.this.startTimePicker.getCurrentMinute();
                int end_hour = TimePickerActivity.this.endTimePicker.getCurrentHour();
                int end_minute = TimePickerActivity.this.endTimePicker.getCurrentHour();

                Intent resultIntent = new Intent(TimePickerActivity.this, SettingsActivity.class);
                resultIntent.putExtra("app_name", TimePickerActivity.this.title);

                resultIntent.putExtra("start_hour", start_hour);
                resultIntent.putExtra("start_minute", start_minute);
                resultIntent.putExtra("end_hour", end_hour);
                resultIntent.putExtra("end_minute", end_minute);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
