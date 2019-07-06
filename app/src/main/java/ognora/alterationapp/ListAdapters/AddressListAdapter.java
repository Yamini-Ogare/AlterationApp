package ognora.alterationapp.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import ognora.alterationapp.Interfaces.OnRadioButtonClickListerner;
import ognora.alterationapp.Model.AddressModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.AddressFormActivity;
import ognora.alterationapp.View.ProductActivity;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder >{

    ArrayList<AddressModel> arrayList;
    Context context ;
    private int lastSelectedPosition = -1;
    public OnRadioButtonClickListerner radioButtonClickListerner;


    public AddressListAdapter(ArrayList<AddressModel> arrayList, Context context, OnRadioButtonClickListerner radioButtonClickListerner) {
        this.arrayList = arrayList;
        this.context = context;
        this.radioButtonClickListerner = radioButtonClickListerner ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address, viewGroup, false);
        return new AddressListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String address ;
        Boolean present;
        if(arrayList.get(position).getLandmark().equalsIgnoreCase(""))
        { address = arrayList.get(position).getArea()+", "+arrayList.get(position).getCity()+", "+arrayList.get(position).getState()+"- "+
                    arrayList.get(position).getPincode();
        present = false;
        }
        else {
            address = arrayList.get(position).getArea() + ", " + arrayList.get(position).getLandmark() + ", " + arrayList.get(position).getCity() + ", " + arrayList.get(position).getState() + "- " +
                    arrayList.get(position).getPincode();
            present = true;
        }

        holder.textView.setText(address);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AddressFormActivity.class);
                intent.putExtra("address_model", arrayList.get(position));
                context.startActivity(intent);


            }
        });

        holder.radioButton.setChecked(lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RadioButton radioButton;
        public TextView textView ;
        public Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.radio_address);
            textView = itemView.findViewById(R.id.address);
            button = itemView.findViewById(R.id.button_edit);

           radioButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   lastSelectedPosition = getAdapterPosition();
                   notifyDataSetChanged();

                   radioButtonClickListerner.onRadioButtonClick(arrayList.get(lastSelectedPosition));
               }
           });

        }
    }
}
