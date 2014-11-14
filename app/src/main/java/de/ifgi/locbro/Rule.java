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
     * Create a Rule object from a String
     *
     * @param ruleAsString String to be transformed to a Rule
     * @return Rule object
     */
    public static Rule createRuleFromString(String ruleAsString) {
        int start_hour = Integer.parseInt(ruleAsString.substring(0, 2));
        int start_minute = Integer.parseInt(ruleAsString.substring(2, 4));
        int end_hour = Integer.parseInt(ruleAsString.substring(4, 6));
        int end_minute = Integer.parseInt(ruleAsString.substring(6, 8));
        double lat = Double.parseDouble(ruleAsString.substring(8, 17));
        double lng = Double.parseDouble(ruleAsString.substring(17, 27));

        return new Rule(start_hour, start_minute, end_hour, end_minute, lat, lng);
    }

    public String toString() {
        String result = "";
        if (this.start_hour < 10) {
            result += "0" + this.start_hour;
        } else {
            result += this.start_hour;
        }
        if (this.start_minute < 10) {
            result += "0" + this.start_minute;
        } else {
            result += this.start_minute;
        }
        if (this.end_hour < 10) {
            result += "0" + this.end_hour;
        } else {
            result += this.end_hour;
        }
        if (this.end_minute < 10) {
            result += "0" + this.end_minute;
        } else {
            result += this.end_minute;
        }
        if (this.lat < 10 && this.lat >= 0) {
            result += "00" + this.lat;
        } else if (this.lat < 100 && this.lat > 0) {
            result += "0" + this.lat;
        } else if (this.lat < 0 && this.lat > -10) {
            result += "-00" + (-1 * this.lat);
        } else if (this.lat < 0 && this.lat > -100) {
            result += "-0" + (-1 * this.lat);
        } else {
            result += this.lat;
        }
        if (this.lng < 10 && this.lng >= 0) {
            result += "00" + this.lng;
        } else if (this.lng < 100 && this.lng > 0) {
            result += "0" + this.lng;
        } else if (this.lng < 0 && this.lng > -10) {
            result += "-00" + (-1 * this.lng);
        } else if (this.lng < 0 && this.lng > -100) {
            result += "-0" + (-1 * this.lng);
        } else {
            result += this.lng;
        }
        return result;
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
