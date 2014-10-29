package de.ifgi.locbro;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.mapquest.android.maps.BoundingBox;
import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.OverlayItem;

/**
 * Activity to change the preferences for the selected application
 *
 * @author Marius Runde
 */
public class SettingsActivity extends MapActivity implements AdapterView.OnItemSelectedListener {

    /**
     * Title of the selected application
     */
    private String title;

    /**
     * Selected accuracy for application
     */
    private int selectedAccuracy;

    /**
     * Spinner for the accuracy
     */
    private Spinner spinnerAccuracy;

    /**
     * Button for the rules
     */
    private Button btn_rules;

    /**
     * MapView for the real and fake locations to be previewed
     */
    private MapView map;

    /**
     * Real location of a user
     */
    private MyLocationOverlay realLocation;

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

        // Setup the Spinner for the accuracy
        this.spinnerAccuracy = (Spinner) findViewById(R.id.spinner_accuracy);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.accuracy_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccuracy.setAdapter(arrayAdapter);
        spinnerAccuracy.setSelection(this.selectedAccuracy);

        // Setup the Button for the rules
        this.btn_rules = (Button) findViewById(R.id.btn_rules);

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
        // --- Real location ---
        this.realLocation = new MyLocationOverlay(this, map);
        realLocation.enableMyLocation();
        realLocation.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                GeoPoint currentLocation = realLocation.getMyLocation();
                map.getController().animateTo(currentLocation);
                map.getController().setZoom(14);
                map.getOverlays().add(realLocation);
                realLocation.setFollowing(false);
            }
        });
        // --- End of real location ---

        // --- Fake location ---
        Drawable icon = getResources().getDrawable(R.drawable.circle);
        final DefaultItemizedOverlay fakeLocationOverlay = new DefaultItemizedOverlay(icon);
        OverlayItem fakeLocationOverlayItem = new OverlayItem(new GeoPoint(51.963610, 7.613276), "Fake Location", null);
        fakeLocationOverlay.addItem(fakeLocationOverlayItem);
        map.getOverlays().add(fakeLocationOverlay);
        // --- End of fake location ---

        // Create BoundingBox to cover both locations
        GeoPoint real = this.realLocation.getMyLocation();
        GeoPoint fake = fakeLocationOverlay.getCenter();
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
        } else if (view.equals(this.btn_rules)) {
            Intent rulesIntent = new Intent(this, RulesActivity.class);
            rulesIntent.putExtra("app_name", this.title);
            startActivity(rulesIntent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
