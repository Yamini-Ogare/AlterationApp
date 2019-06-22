package ognora.alterationapp.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ognora.alterationapp.Model.ProductModel;


@Database(entities = {ProductModel.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract DataAcessObject productDoa();
    private static ProductDatabase INSTANCE;

    public static ProductDatabase getDatabase(Context context){

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "Product_database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }

        return INSTANCE;

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
