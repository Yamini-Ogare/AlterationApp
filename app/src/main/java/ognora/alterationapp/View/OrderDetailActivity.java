package ognora.alterationapp.View;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import ognora.alterationapp.ListAdapters.BillListAdapter;
import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.OrderViewModel;

public class OrderDetailActivity extends AppCompatActivity {

    TextView delivery_address, delivery_date, expected_date, status ;
    TextView amt;
    RecyclerView pro_bill ;
    Button submit;
    MutableLiveData<ArrayList<CartModel>> arrayList = new MutableLiveData<>();
    BillListAdapter adapter;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        delivery_address = findViewById(R.id.deliveryAddress);
        delivery_date = findViewById(R.id.datetime);
        amt = findViewById(R.id.amount);
        pro_bill = findViewById(R.id.recycler_bill);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.bill_progress);
        expected_date = findViewById(R.id.expected_delivery_date);
        status = findViewById(R.id.status);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.setVisibility(View.VISIBLE);


        Bundle bundle = getIntent().getExtras();
        final String order_model_id = (String) bundle.get("order_model_id");

        OrderViewModel orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        pro_bill.setLayoutManager(layoutManager);
        adapter = new BillListAdapter(arrayList, OrderDetailActivity.this);
        pro_bill.setAdapter(adapter);

       final BillActivity activity = new BillActivity();
         final ArrayList<CartModel> cartArray = new ArrayList<>();

        orderViewModel.getOrderDetail(order_model_id).observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(@Nullable OrderModel orderModel) {
                if(orderModel != null){
                    progressBar.setVisibility(View.GONE);
                    delivery_date.setText(activity.getOnlyDate(orderModel.getPickup_date()) + "\n"
                            + activity.getOnlyTime(orderModel.getPickup_date()));
                    delivery_address.setText(orderModel.getPickup_address());
                    amt.setText(orderModel.getPrice()+"");
                    expected_date.setText("Order pLaced on : "+ activity.getOnlyDate(orderModel.getCreatedAt()));
                    status.setText("Order Status : "+ orderModel.getStatus());

                    for(int i=0; i<orderModel.getProduct().size() ; i++){
                        JsonObject json = orderModel.getProduct().get(i).getAsJsonObject();
                        JsonObject pro = json.getAsJsonObject("product_id");

                        CartModel cartModel = new CartModel(pro.get("_id").getAsString(), pro.get("image_url").getAsString(),
                                pro.get("service_type").getAsString(), pro.get("description").getAsString(),
                                pro.get("is_available").getAsBoolean(),
                                pro.get("price").getAsFloat(), json.get("quantity").getAsInt());

                        cartArray.add(cartModel);

                    }
                    arrayList.setValue(cartArray);

                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
