package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.Serializable;

import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    Button login, signUp;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

      viewModel.checkSharedPreferences().observe(this, new Observer<Boolean>() {
          @Override
          public void onChanged(@Nullable Boolean aBoolean) {
              if(aBoolean)
                  startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
          }
      });

        login = findViewById(R.id.button_login);
        signUp = findViewById(R.id.button_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   startActivity(new Intent(LoginActivity.this, PopLoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  startActivity(new Intent(LoginActivity.this, PopSignupActivity.class));
            }
        });


    }


}
