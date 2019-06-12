package ognora.alterationapp.Data;

import java.util.ArrayList;

import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.Model.ProductModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api  {

    String BASE_URL = "https://tech-tailor.herokuapp.com/";

    @GET("product_by_category/{gender}")
    Call<ArrayList<ProductModel>> getAllProduct(@Path("gender") String gender);

    @GET("get_category")
    Call<ArrayList<CategoryModel>> getAllCategory();

    @GET("products/{id}")
    Call<ProductModel> getProduct(@Path("id") String id);
}
