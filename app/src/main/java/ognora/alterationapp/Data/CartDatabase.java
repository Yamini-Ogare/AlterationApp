package ognora.alterationapp.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ognora.alterationapp.Model.CartModel;


@Database(entities = {CartModel.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public abstract DataAcessObject CartDao();
    private static CartDatabase INSTANCE;

    public static CartDatabase getDatabase(Context context){

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, "Cart_database")
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
