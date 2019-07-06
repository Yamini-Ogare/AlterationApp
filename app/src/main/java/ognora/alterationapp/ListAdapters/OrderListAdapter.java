package ognora.alterationapp.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ognora.alterationapp.Model.OrderModel;
import ognora.alterationapp.R;
import ognora.alterationapp.View.AddressFormActivity;
import ognora.alterationapp.View.BillActivity;
import ognora.alterationapp.View.OrderDetailActivity;

public class OrderListAdapter  extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder >{

    ArrayList<OrderModel> arrayList;
    Context context ;

    public OrderListAdapter(ArrayList<OrderModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order, viewGroup, false);
        return new OrderListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText("Order id: "+ arrayList.get(position).get_id());
        holder.amt.setText("Rs. "+ arrayList.get(position).getPrice());

        // substrings of date String
        holder.date.setText("");

        holder.status.setText("Order status : "+arrayList.get(position).getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("order_model_id", arrayList.get(position).get_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        CardView cardView;
        TextView name, amt, status, date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_order);
            amt = itemView.findViewById(R.id.amount_order);
            status = itemView.findViewById(R.id.status_order);
            date = itemView.findViewById(R.id.expecteddate_order);
            cardView = itemView.findViewById(R.id.item_order);

        }
    }
}
