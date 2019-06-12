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

import ognora.alterationapp.ListAdapters.CategoryListAdapter;
import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    CategoryListAdapter categoryListAdapter ;
    ArrayList<CategoryModel> arrayList = new ArrayList<>();
     public ProgressBar progressBar;
    Toolbar toolbar;
    CategoryViewModel categoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recycler_category);
        progressBar = findViewById(R.id.progress_category);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Category");

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        categoryListAdapter =new CategoryListAdapter(arrayList, CategoryActivity.this);
        recyclerView.setAdapter(categoryListAdapter);



        categoryViewModel.getCategoryList().observe(this, new Observer<ArrayList<CategoryModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<CategoryModel> categoryModels) {

                arrayList.addAll(categoryModels);
                categoryListAdapter.notifyDataSetChanged();
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

            case R.id.cart : // show items of cart
                Intent intent = new Intent(this, CartActivity.class);
                this.startActivity(intent);
                break;
            case R.id.profile :
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
