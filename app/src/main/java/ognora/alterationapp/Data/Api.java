package ognora.alterationapp.Data;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.Model.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api  {

    String BASE_URL = "https://tech-tailor.herokuapp.com/";

    @GET("product_by_category/{gender}")
    Call<ArrayList<ProductModel>> getAllProduct(@Path("gender") String gender);

    @GET("get_category")
    Call<ArrayList<CategoryModel>> getAllCategory();

    @GET("products/{id}")
    Call<ProductModel> getProduct(@Path("id") String id);

    @POST("login")
    Call<ResponseBody> generateOTP(
            @Query("phone_no") String phno
    ) ;

    @POST("login/verify_otp")
    Call<JsonObject> OTPLogin(
            @Query("phone_no") String phno,
            @Query("device_id") String device_id,
            @Query("token") String token
    ) ;

    @POST("signup")
    Call<ResponseBody> signup(
            @Query("phone_no") String phone
    );

    @POST("signup/verify_otp")
    Call<JsonObject> OTPSignup(
      @Query("name") String name,
      @Query("password") String password ,
      @Query("email") String email,
      @Query("phone_no") String phone_no,
      @Query("token") String token,
      @Query("device_id") String device_id
    );


    @GET("get_user_details")
    Call<UserModel> getUser(
            @Query("user_id") String user_id
    );

}
