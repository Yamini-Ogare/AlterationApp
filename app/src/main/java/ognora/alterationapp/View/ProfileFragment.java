package ognora.alterationapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ognora.alterationapp.ListAdapters.ProductListAdapter;
import ognora.alterationapp.Model.UserModel;
import ognora.alterationapp.R;
import ognora.alterationapp.ViewModel.UserViewModel;

public class ProfileFragment extends Fragment {


    TextView name, email, phone ;
    ListView listView ;
    LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final ProgressBar progressBar = getActivity().findViewById(R.id.progress_category);
        progressBar.setVisibility(View.VISIBLE);

        linearLayout = view.findViewById(R.id.profileLayout);
        linearLayout.setVisibility(View.GONE);


        final List<String> address = new ArrayList<>();

        name = view.findViewById(R.id.pname);
        email = view.findViewById(R.id.pmail);
        phone = view.findViewById(R.id.pphone);

        listView = view.findViewById(R.id.plistview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                address );

        listView.setAdapter(arrayAdapter);
        UserModel user = new UserModel();

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getProfile(viewModel.preferences.getString("user_id", "")).observe(ProfileFragment.this, new Observer<UserModel>() {
            @Override
            public void onChanged(@Nullable UserModel userModel) {

                if (userModel != null) {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    name.setText(userModel.getName());
                    email.setText(userModel.getEmail());
                    phone.setText(userModel.getPhone_no());
                    address.addAll(userModel.getAddress());
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

                }
            }
        });

        return view;
    }

}
