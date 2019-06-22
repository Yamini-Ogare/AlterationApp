package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import ognora.alterationapp.ListAdapters.ProductListAdapter;
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.CategoryViewModel;
import ognora.alterationapp.ViewModel.ProductViewModel;

public class ProductActivity extends AppCompatActivity {

    Bundle bundle;
    RecyclerView recyclerView ;
    ArrayList<ProductModel> arrayList = new ArrayList<>();
    ProductListAdapter productListAdapter ;
    ProgressBar progressBar;
    Toolbar toolbar;
    CategoryViewModel categoryViewModel;
    public  ProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recycler_product);
        progressBar = findViewById(R.id.progress_product);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");

        bundle = getIntent().getExtras();
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productListAdapter =new ProductListAdapter(arrayList, 1,ProductActivity.this);
        recyclerView.setAdapter(productListAdapter);

        viewModel.getProductByCategory(bundle.getString("category")).observe(this, new Observer<ArrayList<ProductModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ProductModel> productModels) {
                arrayList.addAll(productModels);
                productListAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);

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

            case R.id.cart : // show items in cart
                Intent intent = new Intent(this, CartActivity.class);
                this.startActivity(intent);
                break;
            case R.id.profile :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
