package ognora.alterationapp.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class CategoryModel implements Serializable {


    String _id;
    String name;
    String image_url;


    public CategoryModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
