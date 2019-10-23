package model;

public class Location {
    int id;
    float latitude;
    float longitude;
    String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Location(int id, float latitude, float longitude, String city) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
}
