package de.ifgi.locbro;

/**
 * @author Marius Runde
 */
public class Rule {

    /**
     * Starting hour
     */
    private int start_hour;
    /**
     * Starting minute
     */
    private int start_minute;
    /**
     * End hour
     */
    private int end_hour;
    /**
     * End minute
     */
    private int end_minute;
    /**
     * Latitude for geo fence
     */
    private double lat;
    /**
     * Longitude for geo fence
     */
    private double lng;

    /**
     * Constructor for the Rule class
     *
     * @param start_hour   Starting hour
     * @param start_minute Starting minute
     * @param end_hour     End hour
     * @param end_minute   End minute
     * @param lat          Latitude for geo fence
     * @param lng          Longitude for geo fence
     */
    public Rule(int start_hour, int start_minute, int end_hour, int end_minute, double lat, double lng) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Edit the Rule
     *
     * @param start_hour   Starting hour
     * @param start_minute Starting minute
     * @param end_hour     End hour
     * @param end_minute   End minute
     * @param lat          Latitude for geo fence
     * @param lng          Longitude for geo fence
     */
    public void editRule(int start_hour, int start_minute, int end_hour, int end_minute, double lat, double lng) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.lat = lat;
        this.lng = lng;
    }

    public int getStart_hour() {
        return this.start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_minute() {
        return this.start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public int getEnd_hour() {
        return this.end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_minute() {
        return this.end_minute;
    }

    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
