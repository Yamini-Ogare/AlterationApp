package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import ognora.alterationapp.Model.UserModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class PopLoginActivity extends AppCompatActivity {

    EditText phone;
    public Button generateOTP;
    public Button login;
    public PinEntryEditText pinEntry;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_login);

        final UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        phone = findViewById(R.id.phoneno);
        generateOTP = findViewById(R.id.generateOtp);
        login = findViewById(R.id.login);
        pinEntry = findViewById(R.id.txt_pin_entry);
        progressBar = findViewById(R.id.progress);

        final Bundle bundle = getIntent().getExtras();


        if (bundle != null) {
            final UserModel user = (UserModel) bundle.get("user");
            generateOTP.setVisibility(View.GONE);
            phone.setText(user.getPhone_no());
            pinEntry.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        } else {

            generateOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    viewModel.generateOTP(phone.getText().toString()).observe(PopLoginActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            progressBar.setVisibility(View.GONE);
                            if (aBoolean) {

                                generateOTP.setVisibility(View.GONE);
                                pinEntry.setVisibility(View.VISIBLE);
                                login.setVisibility(View.VISIBLE);


                            }
                        }
                    });

                }
            });
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if (bundle == null) {

                    viewModel.login(phone.getText().toString(), "10100101001",
                            pinEntry.getText().toString()).observe(PopLoginActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            Log.d("Testtt", "aboolean is " + aBoolean);
                            if (aBoolean != null) {
                                progressBar.setVisibility(View.GONE);
                                if (aBoolean) {
                                    startActivity(new Intent(PopLoginActivity.this, CategoryActivity.class));
                                } else {
                                    pinEntry.getText().clear();
                                    Toast.makeText(PopLoginActivity.this, "Wrong OTP. Enter again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    final UserModel user = (UserModel) bundle.get("user");

                    viewModel.signupOTP(user.getName(), user.getPassword(), user.getEmail(), user.getPhone_no(), "10100101003", pinEntry.getText().toString())
                            .observe(PopLoginActivity.this, new Observer<Boolean>() {
                                @Override
                                public void onChanged(@Nullable Boolean aBoolean) {
                                    if(aBoolean!=null){
                                    progressBar.setVisibility(View.GONE);
                                    if (aBoolean) {
                                        startActivity(new Intent(PopLoginActivity.this, CategoryActivity.class));
                                    } else {
                                        pinEntry.getText().clear();
                                        Toast.makeText(PopLoginActivity.this, "Wrong OTP. Enter again", Toast.LENGTH_SHORT).show();
                                    }}
                                }
                            });
                }

            }
        });


    }
}
