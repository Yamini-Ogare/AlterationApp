package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ognora.alterationapp.ListAdapters.CategoryListAdapter;
import ognora.alterationapp.ListAdapters.OrderListAdapter;
import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderListAdapter orderListAdapter;
    Toolbar toolbar;
    ArrayList<OrderModel> arrayList = new ArrayList<>();
    ProgressBar progressBar ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerView = findViewById(R.id.recycler_orders);
        progressBar = findViewById(R.id.progress_orders);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        orderListAdapter =new OrderListAdapter(arrayList, OrdersActivity.this);
        recyclerView.setAdapter(orderListAdapter);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllOrders().observe(this, new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<OrderModel> orderModels) {
                if(orderModels != null) {
                    progressBar.setVisibility(View.GONE);
                    if (orderModels.size() > 0) {
                        arrayList.addAll(orderModels);
                        orderListAdapter.notifyDataSetChanged();
                    }
                    else
                        Toast.makeText(OrdersActivity.this, "Sorry! No orders Placed", Toast.LENGTH_SHORT).show();
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
