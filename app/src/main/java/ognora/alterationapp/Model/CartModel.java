package ognora.alterationapp.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Cart_Table")
public class CartModel {

    @PrimaryKey
    @NonNull
    String product_id ;

    @ColumnInfo
    String img_url;
    @ColumnInfo
    String name;
    @ColumnInfo
    String description;
    @ColumnInfo
    float price;
    @ColumnInfo
    int count ;
    @ColumnInfo
    boolean is_available ;

    public CartModel() {
    }

    public CartModel(@NonNull String product_id, String img_url, String name, String description, boolean is_available, float price, int count) {
        this.product_id = product_id;
        this.img_url = img_url;
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.is_available = is_available;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(@NonNull String product_id) {
        this.product_id = product_id;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}
