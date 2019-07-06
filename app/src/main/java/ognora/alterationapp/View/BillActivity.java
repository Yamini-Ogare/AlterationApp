package ognora.alterationapp.View;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ognora.alterationapp.ListAdapters.BillListAdapter;
import ognora.alterationapp.ListAdapters.CategoryListAdapter;
import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.OrderViewModel;
import ognora.alterationapp.ViewModel.ProductViewModel;

public class BillActivity extends AppCompatActivity {

    TextView delivery_address, delivery_date ;
    TextView amt;
    RecyclerView pro_bill ;
    Button submit;
    float amount ;
    MutableLiveData<ArrayList<CartModel>> arrayList = new MutableLiveData<>();
    BillListAdapter adapter;
    ProgressBar progressBar;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        delivery_address = findViewById(R.id.deliveryAddress);
        delivery_date = findViewById(R.id.datetime);
        amt = findViewById(R.id.amount);
        pro_bill = findViewById(R.id.recycler_bill);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.bill_progress);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Place Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        final String address = (String) bundle.get("bill_address");
        final String date = (String) bundle.get("date");
        final String address_id = (String) bundle.get("address_id");

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        delivery_address.setText(address);
        delivery_date.setText(getOnlyDate(date) + "\n" + getOnlyTime(date));

        final OrderViewModel orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        final JsonArray product =  new JsonArray();

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        arrayList = (MutableLiveData<ArrayList<CartModel>>) productViewModel.getCartItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        pro_bill.setLayoutManager(layoutManager);
        adapter = new BillListAdapter(arrayList, BillActivity.this);
        pro_bill.setAdapter(adapter);

        if (arrayList.getValue().size() > 0) {

            for (int i = 0; i < arrayList.getValue().size(); ++i) {
                JsonObject item = new JsonObject();
                item.addProperty("product_id", arrayList.getValue().get(i).getProduct_id());
                item.addProperty("quantity", arrayList.getValue().get(i).getCount());

                product.add(item);
            }
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        orderViewModel.placeOrder(product, address_id , date, amount).observe(BillActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!=null){

                    if(aBoolean){
                        startActivity(new Intent(BillActivity.this, CategoryActivity.class));
                    }else
                        startActivity(new Intent(BillActivity.this, CartActivity.class));

                }
            }
        });
            }
        });
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Bundle extra = intent.getExtras();
            if(extra!=null) {
                amount = extra.getFloat("Amount", 0);
                amt.setText(amount + "");
            }

            else
                amount = 0;

           }
    };

    public String getOnlyDate(String strDate){

        try {
            Date date=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strDate);
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
            String onlyDateStr=sdf.format(date);

            Log.d("onlyDate",onlyDateStr);
            return onlyDateStr;

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getOnlyTime(String strDate){
        try {
            Date date=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strDate);

            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
            String onlyTimestr=sdf .format(date);
            Log.d("onlyTime",onlyTimestr);

            return onlyTimestr;

        } catch (ParseException e) {
            e.printStackTrace();
            return " ";
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
