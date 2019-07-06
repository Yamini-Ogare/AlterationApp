package ognora.alterationapp.Model;

import com.google.gson.JsonElement;

import java.io.Serializable;

public class AddressModel implements Serializable {

    String _id ;
    int pincode;
    String area;
    String city;
    String state;
    String landmark;

    public AddressModel(String _id, int pincode, String area, String city, String state, String landmark) {
        this._id = _id;
        this.pincode = pincode;
        this.area = area;
        this.city = city;
        this.state = state;
        this.landmark = landmark;
    }

    public AddressModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }


}
