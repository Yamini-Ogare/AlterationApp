package ognora.alterationapp.Data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import ognora.alterationapp.Model.CartModel;

@Dao
public interface DataAcessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void addProduct(CartModel Model);

    @Query("SELECT * From Cart_Table")
    CartModel[] getAllProducts();

    @Delete
    void deleteProduct(CartModel cartModel);

    @Query("SELECT count From Cart_Table where product_id = :id")
    int getItem(String id);

    @Query("UPDATE Cart_Table set count=:cnt where product_id = :id")
    void updateCount(int cnt, String id);
}