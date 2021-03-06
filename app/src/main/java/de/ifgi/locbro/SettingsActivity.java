package de.ifgi.locbro;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.mapquest.android.maps.BoundingBox;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.OverlayItem;

/**
 * Activity to change the preferences for the selected application
 *
 * @author Marius Runde
 */
public class SettingsActivity extends MapActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    /**
     * Title of the selected application
     */
    private String title;

    /**
     * Selected accuracy for application
     */
    private int selectedAccuracy;

    /**
     * Selected notification (enabled/disabled)
     */
    private boolean selectedNotification;

    /**
     * Spinner for the accuracy
     */
    private Spinner spinnerAccuracy;

    /**
     * Checkbox for the notifications
     */
    private CheckBox checkBox;

    /**
     * Button for the time frame
     */
    private Button btn_timeframe;
    // Time chosen by the user before
    private int hour;
    private int minute;

    /**
     * MapView for the real and fake locations to be previewed
     */
    private MapView map;

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
        this.title = intent.getStringExtra("app_name");
        setTitle(this.title);
        this.selectedAccuracy = intent.getIntExtra("selected_accuracy", 0);
        this.selectedNotification = intent.getBooleanExtra("selected_notification", false);
        this.hour = intent.getIntExtra("selected_hour", 0);
        this.minute = intent.getIntExtra("selected_minute", 0);

        // Setup the Spinner for the accuracy
        this.spinnerAccuracy = (Spinner) findViewById(R.id.spinner_accuracy);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.accuracy_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccuracy.setAdapter(arrayAdapter);
        spinnerAccuracy.setSelection(this.selectedAccuracy);
        spinnerAccuracy.setOnItemSelectedListener(this);

        // Setup the CheckBox for the notifications
        this.checkBox = (CheckBox) findViewById(R.id.cb_notifications);
        checkBox.setActivated(selectedNotification);

        // Setup the Button for the time frame
        this.btn_timeframe = (Button) findViewById(R.id.gui_btn_timeframe);
        btn_timeframe.setOnClickListener(this);

        // Setup the MapView
        this.map = (MapView) findViewById(R.id.map);
        map.getController().setZoom(9);
        map.getController().setCenter(new GeoPoint(38.892155, -77.036195));
        map.setBuiltInZoomControls(false);
        setupLocations();
    }

    /**
     * Setup the real and the fake location to be displayed on the MapView
     */
    private void setupLocations() {
        // Remove previously drawn locations (if available)
        if (map.getOverlays().size() > 0) {
            map.getOverlays().clear();
            map.getController().setZoom(15);
        }

        // Get the icon for the locations
        Drawable realIcon = getResources().getDrawable(R.drawable.circle_green);
        Drawable fakeIcon = getResources().getDrawable(R.drawable.circle_red);

        // --- Real location ---
        final DefaultItemizedOverlay realOverlay = new DefaultItemizedOverlay(realIcon);
        OverlayItem realOverlayItem = new OverlayItem(new GeoPoint(51.969031, 7.595659), "Real Location", "User's Location");
        realOverlay.addItem(realOverlayItem);
        map.getOverlays().add(realOverlay);
        // --- End of real location ---

        // --- Fake locations ---
        final DefaultItemizedOverlay fakeOverlay = new DefaultItemizedOverlay(fakeIcon);
        OverlayItem fakeOverlayItem = null;
        switch (this.selectedAccuracy) {
            case 0:
                fakeOverlayItem = new OverlayItem(new GeoPoint(51.969031, 7.595659), "Fake Location", "Real Location");
                break;
            case 1:
                fakeOverlayItem = new OverlayItem(new GeoPoint(51.967584, 7.599082), "Fake Location", "Neighborhood");
                break;
            case 2:
                fakeOverlayItem = new OverlayItem(new GeoPoint(51.961242, 7.629426), "Fake Location", "City");
                break;
            case 3:
                fakeOverlayItem = new OverlayItem(new GeoPoint(51.551928, 7.570605), "Fake Location", "Region");
                break;
            case 4:
                fakeOverlayItem = new OverlayItem(new GeoPoint(51.019324, 10.366229), "Fake Location", "Country");
                break;
            case 5:
                fakeOverlayItem = new OverlayItem(new GeoPoint(44.971513, -93.242928), "Fake Location", "Static Fake Location");
                break;
        }
        // --- End of fake locations ---

        if (this.selectedAccuracy != 0) {
            // Display the fake location
            fakeOverlay.addItem(fakeOverlayItem);
            map.getOverlays().add(fakeOverlay);
            // Create BoundingBox to cover both locations
            GeoPoint real = realOverlay.getCenter();
            GeoPoint fake = fakeOverlay.getCenter();
            if (real.getLatitude() > fake.getLatitude()) {
                double tempLat = real.getLatitude();
                real = new GeoPoint(fake.getLatitude(), real.getLongitude());
                fake = new GeoPoint(tempLat, fake.getLongitude());
            }
            if (real.getLongitude() > fake.getLongitude()) {
                double tempLng = real.getLongitude();
                real = new GeoPoint(real.getLatitude(), fake.getLongitude());
                fake = new GeoPoint(fake.getLatitude(), tempLng);
            }
            BoundingBox bbox = new BoundingBox(real, fake);
            // Zoom and pan the map to the desired area
            map.getController().zoomToSpan(bbox);
            map.getController().zoomOut();
        } else {
            map.getController().animateTo(realOverlay.getCenter());
        }
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
        if (id == R.id.menu_reset_preferences) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.selectedAccuracy = adapterView.getSelectedItemPosition();
        setupLocations();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }

    @Override
    public void onClick(View view) {
        pickTime();
    }

    /**
     * Open a TimePickerDialog to set the time frame
     */
    private void pickTime() {
        TimePickerDialog tpd = new TimePickerDialog(getApplicationContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                    }
                }, hour, minute, true);
        tpd.show();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent(this, SettingsActivity.class);
        resultIntent.putExtra("app_name", this.title);
        resultIntent.putExtra("accuracy", this.selectedAccuracy);
        resultIntent.putExtra("notification", checkBox.isActivated());
        resultIntent.putExtra("hour", this.hour);
        resultIntent.putExtra("minute", this.hour);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}