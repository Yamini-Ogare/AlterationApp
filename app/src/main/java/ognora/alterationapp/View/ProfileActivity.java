package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ognora.alterationapp.Model.UserModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email, phone ;
    ListView listView ;
    LinearLayout linearLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ProgressBar progressBar = findViewById(R.id.progress_profile);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<String> address = new ArrayList<>();

        name = findViewById(R.id.pname);
        email = findViewById(R.id.pmail);
        phone = findViewById(R.id.pphone);

        listView = findViewById(R.id.plistview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                address );

        listView.setAdapter(arrayAdapter);
        UserModel user = new UserModel();

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getProfile(viewModel.preferences.getString("user_id", "")).observe(ProfileActivity.this, new Observer<UserModel>() {
            @Override
            public void onChanged(@Nullable UserModel userModel) {

                if (userModel != null) {
                    progressBar.setVisibility(View.GONE);
                    name.setText(userModel.getName());
                    email.setText(userModel.getEmail());
                    phone.setText(userModel.getPhone_no());
                    address.addAll(userModel.getAddress());
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
