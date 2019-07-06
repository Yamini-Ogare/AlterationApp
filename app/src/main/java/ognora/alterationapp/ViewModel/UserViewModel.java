package ognora.alterationapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import ognora.alterationapp.Data.Api;
import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.Model.UserModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.CategoryActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends AndroidViewModel {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    MutableLiveData<Boolean> exists = new MutableLiveData<>();
    Context context;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getBaseContext();
        preferences = context.getSharedPreferences("myPref",Context.MODE_PRIVATE );
        editor = preferences.edit();
    }

    public LiveData<Boolean> login(String phone, String device_id, String token) {

        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.OTPLogin(phone, device_id, token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                Toast.makeText(context, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {

                    if (response.body().get("success").getAsBoolean()) {
                        success.setValue(true);
                        editor.putString("user_id", response.body().get("user_id").getAsString()).apply();
                    } else
                        success.setValue(false);
                } else
                    success.setValue(false);
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                success.setValue(false);
            }
        });


        return success;
    }

    public LiveData<Boolean> generateOTP(String phno) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.generateOTP(phno);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    success.setValue(true);


                } else
                    success.setValue(false);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                success.setValue(false);

            }
        });


        return success;

    }


    public LiveData<Boolean> signup(String phone) {

        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.signup(phone);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful())
                    success.setValue(true);
                else
                    success.setValue(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                success.setValue(false);
            }
        });


        return success;


    }

    public LiveData<Boolean> signupOTP(String name, String password, String mail, String phno, String device, String token) {
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        success.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.OTPSignup(name, password, mail, phno, token, device);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(context, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful())
                    success.setValue(response.body().get("success").getAsBoolean());
                else
                    success.setValue(false);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                success.setValue(false);
            }
        });


        return success;


    }


    public LiveData<Boolean> checkSharedPreferences() {
        String user_id = preferences.getString("user_id", "");

        if (!user_id.equals("")) {
            exists.setValue(true);
        } else
            exists.setValue(false);
        return exists;
    }


    public LiveData<UserModel> getProfile(String user_id) {
        final MutableLiveData<UserModel> user = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<UserModel> call = api.getUser(user_id);

        call.enqueue(new Callback<UserModel>() {


            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful())
                     user.setValue(response.body());
                else
                    Toast.makeText(context, response.code()+" error.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });


        return user;
    }

    public LiveData<Boolean> logout(){
        final MutableLiveData<Boolean> logout = new MutableLiveData<>();


                    editor.putString("user_id","").commit();
                    String is = preferences.getString("user_id","");
                    logout.setValue(true);

                    Toast.makeText(context, "Logged Out Successfully.", Toast.LENGTH_SHORT).show();


         return logout;

    }

    public LiveData<ArrayList<OrderModel>> getAllOrders(){
        final MutableLiveData<ArrayList<OrderModel>> arrayList = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ArrayList<OrderModel>> call = api.getAllOrders(preferences.getString("user_id",""));

        call.enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                if(response.isSuccessful()){

                    arrayList.setValue(response.body());                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {

            }
        });

        return  arrayList;
    }
}
