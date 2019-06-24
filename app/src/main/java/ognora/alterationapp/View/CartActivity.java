package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

import ognora.alterationapp.ListAdapters.CartListAdapter;
import ognora.alterationapp.ListAdapters.ProductListAdapter;
import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.ProductViewModel;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartListAdapter adapter;
    Toolbar toolbar;
    Button button;
    ArrayList<CartModel> arrayList = new ArrayList<>();
    public  ProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_cart);
        button = findViewById(R.id.place_order);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter =new CartListAdapter(arrayList, CartActivity.this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        viewModel.getCartItems().observe(this, new Observer<ArrayList<CartModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<CartModel> Models) {
                arrayList.addAll(Models);
                adapter.notifyDataSetChanged();
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.profile :
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
