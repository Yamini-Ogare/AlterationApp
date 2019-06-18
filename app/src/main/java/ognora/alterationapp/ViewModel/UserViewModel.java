package ognora.alterationapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;

import ognora.alterationapp.Data.Api;
import ognora.alterationapp.View.LoginActivity;
import ognora.alterationapp.View.PopLoginActivity;
import ognora.alterationapp.View.PopSignupActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends AndroidViewModel {

    Context context;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.context = getApplication().getBaseContext();
    }

    public LiveData<Boolean> login(String phone, String device_id, String token){

        final MutableLiveData <Boolean> success = new MutableLiveData<>();
       success.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.OTPLogin(phone, device_id , token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                Toast.makeText(context, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()) {

                    if (response.body().get("success").getAsBoolean()){
                        success.setValue(true);
                    }else
                        success.setValue(false);
                }  else
                    success.setValue(false);
                }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                  success.setValue(false);
            }
        });


        return success;
    }

    public LiveData<Boolean> generateOTP(String phno){
        final MutableLiveData <Boolean> success = new MutableLiveData<>();
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

                if(response.isSuccessful()){
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





    public LiveData<Boolean> signup(String phone){

        final MutableLiveData <Boolean> success = new MutableLiveData<>();
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
                if(response.isSuccessful())
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

    public LiveData<Boolean> signupOTP(String name, String password,  String mail , String phno, String device, String token){
        final MutableLiveData <Boolean> success = new MutableLiveData<>();
        success.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<JsonObject> call = api.OTPSignup(name, password,mail,phno,token,device);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(context, response.body().get("message").toString(), Toast.LENGTH_SHORT).show();

                if(response.isSuccessful())
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
}
