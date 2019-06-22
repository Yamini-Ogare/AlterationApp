package ognora.alterationapp.Data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import ognora.alterationapp.Model.ProductModel;

@Dao
public interface DataAcessObject  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void addProduct(ProductModel productModel);

    @Query("SELECT * From ProductTable")
    ProductModel[] getAllProducts();

    @Delete
    void deleteProduct(ProductModel productModel);
}
