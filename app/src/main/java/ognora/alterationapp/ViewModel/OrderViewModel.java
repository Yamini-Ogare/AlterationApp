package ognora.alterationapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ognora.alterationapp.Data.Api;
import ognora.alterationapp.Model.AddressModel;
import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.View.AddressFormActivity;
import ognora.alterationapp.View.SelectAddressActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class OrderViewModel extends AndroidViewModel {

    Context context;
    MutableLiveData<ArrayList<AddressModel>> addressArrayList = new MutableLiveData<>();
    SharedPreferences preferences;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getBaseContext();
        preferences = context.getSharedPreferences("myPref",Context.MODE_PRIVATE );
        //kha use krna h
    }

    public LiveData<ArrayList<AddressModel>> getAllAddress(String user_id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.getAddress(user_id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                try {
                    ArrayList<AddressModel> addressModels = new ArrayList<>();
                    JsonArray jsonArray = (JsonArray) response.body().get("address");

                     for (int i = 0; i < jsonArray.size(); i++) {
                         AddressModel addressModel = new AddressModel();
                         JsonObject addressObj = (JsonObject) jsonArray.get(i);

                         addressModel.set_id(addressObj.get("_id").getAsString());
                         addressModel.setArea(addressObj.get("area").getAsString());
                         addressModel.setCity(addressObj.get("city").getAsString());
                         addressModel.setState(addressObj.get("state").getAsString());
                         addressModel.setLandmark(addressObj.get("landmark").getAsString());
                         addressModel.setPincode(addressObj.get("pincode").getAsInt());
                         addressModels.add(addressModel);

                 }

                    addressArrayList.setValue(addressModels);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d("msg", t.getMessage());
            }
        });


        return addressArrayList;
    }


    public LiveData<Boolean> editAddress(AddressModel addressModel){

        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(null);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.editAddress(addressModel.get_id(), addressModel.getState(), addressModel.getPincode(),
                addressModel.getCity(),addressModel.getArea(), addressModel.getLandmark());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(context, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                    success.setValue(true);
                }
                else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                     success.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                success.setValue(false);
                Toast.makeText(context, t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

        return success;

    }

    public LiveData<String> addAddress(AddressModel addressModel){


   final MutableLiveData<String> addressid = new MutableLiveData<>();
   addressid.setValue(null);

   String user_id = preferences.getString("user_id","");
   //hr jgh same prefrence use krna "myPref" ok chlao yup

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.addAddress(user_id,
                addressModel.getState(), addressModel.getPincode(),
                addressModel.getCity(),addressModel.getArea(), addressModel.getLandmark());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(context, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                     addressid.setValue(response.body().get("address_id").getAsString());
                }
                else
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

        return addressid;

    }


    public LiveData<Boolean> placeOrder(JsonArray product, String address , String pickup_date, float price){
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(null);
        String user_id = preferences.getString("user_id","");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        Api api = retrofit.create(Api.class);

        HashMap<String ,Object> map = new HashMap<>();
        map.put("product", product);
        map.put("user_id", user_id);
        map.put("pickup_date", pickup_date);
        map.put("pickup_address", address);
        map.put("price", price);

        Call<JsonObject> call = api.placeOrder(map);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(context, response.body().get("message").getAsString(), Toast.LENGTH_LONG).show();
                if(response.isSuccessful()){

                    success.setValue(true);
                }
                else {
                    success.setValue(false);
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                success.setValue(false);
            }
        });


        return success;

    }


    public LiveData<OrderModel> getOrderDetail(String order_id){
        final MutableLiveData<OrderModel> orderModel = new MutableLiveData<>();
        orderModel.setValue(null);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.getOrderDetails(order_id);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Gson gson= new Gson();
                    OrderModel obj = gson.fromJson(response.body().getAsJsonObject("order").toString(),OrderModel.class);
                    obj.setPickup_address(response.body().get("address").getAsString());
                    orderModel.setValue(obj);
                }
                else
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return  orderModel;
    }




}
