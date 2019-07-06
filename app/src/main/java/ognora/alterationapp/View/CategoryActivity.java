package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ognora.alterationapp.ListAdapters.CategoryListAdapter;
import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.CategoryViewModel;
import ognora.alterationapp.ViewModel.UserViewModel;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView ;
    CategoryListAdapter categoryListAdapter ;
    ArrayList<CategoryModel> arrayList = new ArrayList<>();
     public ProgressBar progressBar;
    Toolbar toolbar;
    CategoryViewModel categoryViewModel;
    UserViewModel userViewModel;
    private DrawerLayout drawer ;


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


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
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
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.home : break;
            case R.id.profile :
                startActivity(new Intent(CategoryActivity.this, ProfileActivity.class));
                break;

            case R.id.orders :
                startActivity(new Intent(CategoryActivity.this, OrdersActivity.class));
                break;

            case R.id.about :
                startActivity(new Intent(CategoryActivity.this, AboutActivity.class));
                break;

            case R.id.logout :
                userViewModel.logout().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {

                    if(aBoolean){
                        startActivity(new Intent(CategoryActivity.this, LoginActivity.class));
                    }

                }
            });
            break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

}
