package com.daroonapp.library.Transactions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daroonapp.library.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private List<Transactions> mainlist;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView status,date,transactionId,hours,price;
        public ImageView imgstatus;

        public MyViewHolder(View view) {
            super(view);
            status = (TextView) view.findViewById(R.id.txtstatus);
            date = (TextView) view.findViewById(R.id.txtdate);
            transactionId = (TextView) view.findViewById(R.id.txttransaction_id);
            price = (TextView) view.findViewById(R.id.txtprice);
            imgstatus = (ImageView) view.findViewById(R.id.imgstatus);
        }

    }

    public TransactionAdapter(Context applicationContext, int firstrow, List<Transactions> moviesList) {
        this.mainlist = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.status.setText(mainlist.get(position).getStatus());
        if(mainlist.get(position).getStatus().equalsIgnoreCase("ناموفق")){
            holder.imgstatus.setImageResource(R.drawable.status_red);
        } else if(mainlist.get(position).getStatus().equalsIgnoreCase("موفق")){
            holder.imgstatus.setImageResource(R.drawable.status_green);
        }else if(mainlist.get(position).getStatus().equalsIgnoreCase("در حال پرداخت")){
            holder.imgstatus.setImageResource(R.drawable.status_orange);
        }
        holder.date.setText(mainlist.get(position).getDate());
        holder.transactionId.setText(mainlist.get(position).getId());
        holder.price.setText(mainlist.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mainlist.size();
    }


}