package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
    FrameLayout frameLayout ;
    boolean doubleBackToExitPressedOnce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        doubleBackToExitPressedOnce = false;

        frameLayout = findViewById(R.id.fragment_container);

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
            case R.id.profile :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                int fragments = getSupportFragmentManager().getBackStackEntryCount();
                if(fragments!=0){
                if (fragments == 1) {
                    //   getFragmentManager().popBackStack();
                    frameLayout.setVisibility(View.GONE);
                    getFragmentManager().popBackStack();
                } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                }else {
                    super.onBackPressed(); }}

                else{
                if (doubleBackToExitPressedOnce) {

                    finishAffinity();
                }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 2000);
                }
            }}



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.home :   frameLayout.setVisibility(View.GONE); break;
            case R.id.profile : frameLayout.setVisibility(View.VISIBLE);
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack("profile").commit();
                 break;

            case R.id.orders :  frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrderFragment()).addToBackStack("order").commit();

                break;

            case R.id.about :  frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).addToBackStack("about").commit();

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
        toolbar.setTitle(menuItem.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
