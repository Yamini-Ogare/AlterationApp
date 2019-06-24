package ognora.alterationapp.ListAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ognora.alterationapp.Model.CartModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.CartActivity;

public class CartListAdapter  extends RecyclerView.Adapter<CartListAdapter.MyViewHolder > {

    ArrayList<CartModel> arrayList ;
    Context context;

    public CartListAdapter(ArrayList<CartModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position).getImg_url())
                .into(holder.imageView);
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText("Rs. " +arrayList.get(position).getPrice());
        holder.qty.setText(arrayList.get(position).getCount()+"");

        if(arrayList.get(position).isIs_available()){
            holder.available.setText("Available");
        } else {
            holder.available.setText("Currently unavailable");
        }

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CartActivity)context).viewModel.removeFromCart(arrayList.get(position));
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  arrayList.get(position).setCount(arrayList.get(position).getCount()+1);
                  holder.qty.setText(String.valueOf(Integer.parseInt((String) holder.qty.getText())+1));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.get(position).setCount(arrayList.get(position).getCount()-1);
                holder.qty.setText(String.valueOf(Integer.parseInt((String) holder.qty.getText())-1));
                if(arrayList.get(position).getCount()==0)
                {
                    ((CartActivity)context).viewModel.removeFromCart(arrayList.get(position));
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, available, qty;
        Button remove, plus, minus;
        ImageView imageView ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_product);
            price=  itemView.findViewById(R.id.price_product);
            available = itemView.findViewById(R.id.is_available);
            qty = itemView.findViewById(R.id.qty_product);
            remove = itemView.findViewById(R.id.removeFromCart);
            plus = itemView.findViewById(R.id.button_plus);
            minus = itemView.findViewById(R.id.button_minus);
            imageView = itemView.findViewById(R.id.image_product);

        }
    }
}
