package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ognora.alterationapp.Interfaces.OnRadioButtonClickListerner;
import ognora.alterationapp.ListAdapters.AddressListAdapter;
import ognora.alterationapp.ListAdapters.CartListAdapter;
import ognora.alterationapp.Model.AddressModel;
import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.OrderViewModel;
import ognora.alterationapp.ViewModel.ProductViewModel;
import ognora.alterationapp.ViewModel.UserViewModel;

public class SelectAddressActivity extends AppCompatActivity implements OnRadioButtonClickListerner {

    public UserViewModel userViewModel ;
    RecyclerView recyclerView ;
    AddressListAdapter adapter;
    Toolbar toolbar;
    CardView addnewAddress , go ;
    ArrayList<AddressModel> arrayList = new ArrayList<>();
    public OrderViewModel orderViewModel;
    AddressModel addressModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        recyclerView = findViewById(R.id.recycler_address);
        addnewAddress = findViewById(R.id.addnewaddress);
        go = findViewById(R.id.go);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter =new AddressListAdapter(arrayList, SelectAddressActivity.this, this);
        recyclerView.setAdapter(adapter);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        orderViewModel.getAllAddress(userViewModel.preferences.getString("user_id","")).observe(this, new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<AddressModel> addressModels) {
                arrayList.addAll(addressModels);
                adapter.notifyDataSetChanged();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectAddressActivity.this, PickupDetailActivity.class);
                intent.putExtra("pickup_address", addressModel);
                startActivity(intent);

            }
        });

        addnewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SelectAddressActivity.this, AddressFormActivity.class));

            }
        });


    }

    @Override
    public void onRadioButtonClick(AddressModel addressModel) {

        this.addressModel = addressModel;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
