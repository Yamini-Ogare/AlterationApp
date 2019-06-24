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
import ognora.alterationapp.Data.CartDatabase;
import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.Model.ProductModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductViewModel extends AndroidViewModel {
    MutableLiveData<ArrayList<ProductModel>> arrayList = new MutableLiveData<>();
    MutableLiveData<ArrayList<CartModel>> cartlist = new MutableLiveData<>();
    Context context;
    private CartDatabase cartDatabase;

    CartModel cartModel;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        this.context = getApplication().getBaseContext();
        cartDatabase = CartDatabase.getDatabase(application);
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

    public void addToCart(ProductModel product, int cnt){
        int cart = cartDatabase.CartDao().getItem(product.get_id());
        int count;

        if(cart != 1) {
         count = cart + 1 ;

        } else
            count =cnt;

            cartModel = new CartModel(product.get_id(), product.getImage_url(),
                    product.getService_type(), product.getDescription(),product.isIs_available(), product.getAlteration_price(), count);
     cartDatabase.CartDao().addProduct(cartModel);
        Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();

    }

    public void removeFromCart(CartModel cartModel){

        cartDatabase.CartDao().deleteProduct(cartModel);
            Toast.makeText(context, "Removed ", Toast.LENGTH_LONG).show();

    }

    public LiveData<ArrayList<CartModel>> getCartItems (){

        ArrayList<CartModel> pro = new ArrayList<>(Arrays.asList(cartDatabase.CartDao().getAllProducts()));
        cartlist.setValue(pro);
        return cartlist ;
    }





}
