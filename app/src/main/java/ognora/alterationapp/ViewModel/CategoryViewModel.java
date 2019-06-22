package ognora.alterationapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import ognora.alterationapp.Data.Api;
import ognora.alterationapp.Model.CategoryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<CategoryModel>> category_array ;
    Context context ;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getBaseContext();
    }


    public LiveData<ArrayList<CategoryModel>> getCategoryList()
    {

        if(category_array == null) {
            category_array = new MutableLiveData<>();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            Api api = retrofit.create(Api.class);
            Call<ArrayList<CategoryModel>> call = api.getAllCategory();

            call.enqueue(new Callback<ArrayList<CategoryModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {

                    category_array.setValue(response.body());

                }

                @Override
                public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

                    Log.d("msg", t.getMessage());

                }
            });
        }


        return category_array ;
    }




}
