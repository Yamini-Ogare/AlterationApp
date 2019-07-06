package ognora.alterationapp.Data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.Model.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

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
            @Query("password") String password,
            @Query("email") String email,
            @Query("phone_no") String phone_no,
            @Query("token") String token,
            @Query("device_id") String device_id
    );


    @GET("get_user_details")
    Call<UserModel> getUser(
            @Query("user_id") String user_id
    );

    @GET("get_address")
    Call<JsonObject> getAddress(
            @Query("user_id") String id
    );

    @PUT("edit_address")
    Call<JsonObject> editAddress(
            @Query("address_id") String address_id,
            @Query("state") String state,
            @Query("pincode") Integer pin,
            @Query("city") String city,
            @Query("area") String area,
            @Query("landmark") String landmark
    );

    @POST("add_new_address")
    Call<JsonObject> addAddress(
            @Query("user_id") String user_id,
            @Query("state") String state,
            @Query("pincode") Integer pin,
            @Query("city") String city,
            @Query("area") String area,
            @Query("landmark") String landmark
    );


    @POST("place_order")
    Call<JsonObject> placeOrder(
            @Body HashMap map
    );

    @GET("get_user_orders")
    Call<ArrayList<OrderModel>> getAllOrders(
            @Query("user_id") String user_id
    );

    @GET("get_order_details")
    Call<JsonObject> getOrderDetails (

            @Query("order_id") String order_id
    );

}
