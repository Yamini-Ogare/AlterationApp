package ognora.alterationapp.ListAdapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.R;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.MyViewHolder > {

    MutableLiveData<ArrayList<CartModel>> arrayList = new MutableLiveData<>();
    Context context;
    float sum ;

    public BillListAdapter(MutableLiveData<ArrayList<CartModel>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        sum =  0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bill, viewGroup, false);
        return new BillListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context)
                .load(arrayList.getValue().get(position).getImg_url())
                .into(holder.img);

        holder.name.setText(arrayList.getValue().get(position).getName());
        holder.price.setText(arrayList.getValue().get(position).getPrice()+"");
        holder.qty.setText(arrayList.getValue().get(position).getCount()+"");


        sum = sum + (arrayList.getValue().get(position).getPrice()* arrayList.getValue().get(position).getCount());

        if(position == (arrayList.getValue().size()-1)){
            Intent intent = new Intent("custom-message");
            intent.putExtra("Amount", sum);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList.getValue() != null)
        return arrayList.getValue().size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price, qty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.pro_img);
            name = itemView.findViewById(R.id.pro_name);
            price = itemView.findViewById(R.id.pro_price);
            qty = itemView.findViewById(R.id.pro_qty);


        }
    }
}
