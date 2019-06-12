package ognora.alterationapp.ListAdapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ognora.alterationapp.Model.CategoryModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.CategoryActivity;
import ognora.alterationapp.View.ProductActivity;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder > {

    ArrayList<CategoryModel> arrayList ;
    Context context ;

    public CategoryListAdapter(ArrayList<CategoryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position).getImage_url())
                .into(myViewHolder.imageView);

        myViewHolder.textView.setText(arrayList.get(position).getName());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("category", arrayList.get(position).getName());
                context.startActivity(intent);
            }
        });

        ((CategoryActivity)context).progressBar.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView ;
        ImageView imageView ;
        TextView textView ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_category);
            imageView = itemView.findViewById(R.id.image_category);
            textView = itemView.findViewById(R.id.name_category);

        }
    }
}
