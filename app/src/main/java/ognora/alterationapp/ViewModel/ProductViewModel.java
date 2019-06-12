package ognora.alterationapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ognora.alterationapp.Data.Api;
import ognora.alterationapp.Data.ProductDatabase;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.View.CartActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductViewModel extends AndroidViewModel {
    MutableLiveData<ArrayList<ProductModel>> arrayList = new MutableLiveData<>();
    Context context;
    private ProductDatabase productDatabase ;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        this.context = getApplication().getBaseContext();
        productDatabase = ProductDatabase.getDatabase(application);
    }

    public MutableLiveData<ArrayList<ProductModel>> getProductByCategory(String categoryname) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<ProductModel>> call = api.getAllProduct(categoryname);

        call.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                arrayList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Log.d("msg", t.getMessage());

            }
        });

        return arrayList;
    }

    public void addToCart(ProductModel product){

     productDatabase.productDoa().addProduct(product);
        Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG);

    }

    public void removeFromCart(ProductModel product){

        productDatabase.productDoa().deleteProduct(product);
            Toast.makeText(context, "Removed ", Toast.LENGTH_LONG);

    }

    public LiveData<ArrayList<ProductModel>> getCartItems (){

        ArrayList<ProductModel> pro = new ArrayList<>(Arrays.asList(productDatabase.productDoa().getAllProducts()));
        arrayList.setValue(pro);
        return arrayList ;
    }





}
