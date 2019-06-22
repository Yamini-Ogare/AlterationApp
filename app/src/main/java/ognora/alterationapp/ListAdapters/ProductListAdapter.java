package ognora.alterationapp.ListAdapters;

import android.content.Context;
import android.content.Intent;
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

import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.CartActivity;
import ognora.alterationapp.View.DescriptionActivity;
import ognora.alterationapp.View.ProductActivity;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder > {
    ArrayList<ProductModel> arrayList ;
    Context context;
    int type;

    public ProductListAdapter(ArrayList<ProductModel> arrayList,int type, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return  type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        if(i==1)
        {v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);}
        else
        {v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart, viewGroup, false);}

        return new ProductListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

            Glide.with(context)
                    .load(arrayList.get(position).getImage_url())
                    .into(myViewHolder.imageView);

            myViewHolder.t1.setText(arrayList.get(position).getService_type());
            myViewHolder.t3.setText("Rs. " + arrayList.get(position).getAlteration_price());

           if(type==1){
            myViewHolder.t2.setText(arrayList.get(position).getDescription());
            myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // add items to cart in room data base

                   ((ProductActivity) context).viewModel.addToCart(arrayList.get(position));
                }
            });

            myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DescriptionActivity.class);
                    intent.putExtra("product", arrayList.get(position));
                    context.startActivity(intent);
                }
            });
        }    else
           {   if(arrayList.get(position).isIs_available())
               myViewHolder.t2.setText("Available");
               else
                  myViewHolder.t2.setText("Not Available");

               myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       // remove from cart
                       ((CartActivity)context).viewModel.removeFromCart(arrayList.get(position));
                       arrayList.remove(position);
                         notifyDataSetChanged();
                   }
               });
           }





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView t1, t2, t3 ;
        Button button ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_product);
            t1 = itemView.findViewById(R.id.name_product);
            t3 = itemView.findViewById(R.id.price_product);
            if (type == 1) {

                t2 = itemView.findViewById(R.id.shortdes_product);
                button = itemView.findViewById(R.id.addToCart);
            } else {
                t2 = itemView.findViewById(R.id.is_available);
                button = itemView.findViewById(R.id.removeFromCart);
            }
        }
    }


}
