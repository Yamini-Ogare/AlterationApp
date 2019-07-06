package ognora.alterationapp.View;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ognora.alterationapp.Model.AddressModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.OrderViewModel;
import ognora.alterationapp.ViewModel.UserViewModel;

public class AddressFormActivity extends AppCompatActivity {

    EditText area, landmark, city, state, pincode ;
    Button save;
    Toolbar toolbar;
    AddressModel finalAddress ;
    public UserViewModel userViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);

        area = findViewById(R.id.area);
        landmark = findViewById(R.id.landmark);
        city  = findViewById(R.id.city);
        state  = findViewById(R.id.state);
        pincode = findViewById(R.id.pin);
        save = findViewById(R.id.saveAddress);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar= findViewById(R.id.pro);

        final Bundle bundle = getIntent().getExtras();

        final OrderViewModel orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if(bundle!=null){
           final AddressModel addressModel = (AddressModel) bundle.get("address_model");
           toolbar.setTitle("Edit Address");

            area.setText(addressModel.getArea());
            landmark.setText(addressModel.getLandmark());
            city.setText(addressModel.getCity());
            state.setText(addressModel.getState());
            pincode.setText(addressModel.getPincode()+"");

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalAddress= new AddressModel(addressModel.get_id(), Integer.parseInt(String.valueOf(pincode.getText())), area.getText().toString(),
                            city.getText().toString(), state.getText().toString(), landmark.getText().toString());

                    orderViewModel.editAddress(finalAddress).observe(AddressFormActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean != null) {
                                if (aBoolean) {
                                    Intent intent = new Intent(AddressFormActivity.this, PickupDetailActivity.class);
                                    intent.putExtra("pickup_address", finalAddress);
                                    startActivity(intent);

                                }
                            }

                        }
                    });

                }
            });

        } else {
            toolbar.setTitle("Add new Address");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkNotEmpty(area) && checkNotEmpty(city) && checkNotEmpty(state) && checkNotEmpty(pincode)) {
                        progressBar.setVisibility(View.VISIBLE);


                        finalAddress = new AddressModel("", Integer.parseInt(String.valueOf(pincode.getText())), area.getText().toString(),
                                city.getText().toString(), state.getText().toString(), landmark.getText().toString());

                        orderViewModel.addAddress(finalAddress).observe(AddressFormActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                if (s != null) {
                                    progressBar.setVisibility(View.GONE);
                                    finalAddress.set_id(s);
                                    Intent intent = new Intent(AddressFormActivity.this, PickupDetailActivity.class);
                                    intent.putExtra("pickup_address", finalAddress);
                                    startActivity(intent);

                                }


                            }
                        });

                    }
                }
            });



        }

    }

    public Boolean checkNotEmpty(EditText e){
        Boolean notempty = false;

        if(TextUtils.isEmpty(e.getText())){
            e.setError("Required Field");
            e.requestFocus();
            notempty = false;

        }else
            notempty = true ;

        return notempty;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
