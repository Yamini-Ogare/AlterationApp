package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ognora.alterationapp.Model.UserModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class PopSignupActivity extends AppCompatActivity {

    EditText name, phone, email, password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_signup);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        button = findViewById(R.id.sign);



        final UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.signup(phone.getText().toString()).observe(PopSignupActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean!=null){
                            if(aBoolean){
                     final UserModel user = new UserModel(name.getText().toString(), phone.getText().toString(), password.getText().toString(), email.getText().toString());


                                Intent intent = new Intent(PopSignupActivity.this, PopLoginActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);


                        }else{
                            startActivity(new Intent(PopSignupActivity.this, LoginActivity.class));
                        }}
                    }
                });
            }
        });

    }
}
