package ognora.alterationapp.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import ognora.alterationapp.Model.ProductModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.CartActivity;
import ognora.alterationapp.View.DescriptionActivity;
import ognora.alterationapp.View.ProductActivity;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder > {
    ArrayList<ProductModel> arrayList ;
    Context context;

    public ProductListAdapter(ArrayList<ProductModel> arrayList, Context context) {

        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);

        return new ProductListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

            Glide.with(context)
                    .load(arrayList.get(position).getImage_url())
                    .into(myViewHolder.imageView);

            myViewHolder.t1.setText(arrayList.get(position).getService_type());
            myViewHolder.t3.setText("Rs. " + arrayList.get(position).getAlteration_price());
            myViewHolder.t2.setText(arrayList.get(position).getDescription());
            myViewHolder.qty.setText("1");

            myViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // add items to cart in room data base

                   ((ProductActivity) context).viewModel.addToCart(arrayList.get(position),Integer.parseInt((String) myViewHolder.qty.getText()));

                }
            });

            myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DescriptionActivity.class);
                    intent.putExtra("product", arrayList.get(position));
                    context.startActivity(intent);
                }
            });

            myViewHolder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    myViewHolder.qty.setText(String.valueOf(Integer.parseInt((String) myViewHolder.qty.getText())+1));



                }
            });

            myViewHolder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if((Integer.parseInt((String) myViewHolder.qty.getText())-1) > 0)
                    myViewHolder.qty.setText(String.valueOf(Integer.parseInt((String) myViewHolder.qty.getText())-1));
                    else
                     myViewHolder.qty.setText("1");
                }
            });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, plus, minus;
        CardView cardView;
        TextView t1, t2, t3 , qty;
        Button button ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_product);
            t1 = itemView.findViewById(R.id.name_product);
            t3 = itemView.findViewById(R.id.price_product);
            t2 = itemView.findViewById(R.id.shortdes_product);
            button = itemView.findViewById(R.id.addToCart);
            cardView = itemView.findViewById(R.id.item_category);
            plus = itemView.findViewById(R.id.button_plus);
            minus = itemView.findViewById(R.id.button_minus);
            qty = itemView.findViewById(R.id.qty_product);

        }
    }


}
