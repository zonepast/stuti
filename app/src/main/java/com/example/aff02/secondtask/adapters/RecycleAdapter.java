package com.example.aff02.secondtask.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aff02.secondtask.R;
import com.example.aff02.secondtask.model.DetailModel;

import java.util.List;

/**
 * Created by AFF02 on 09-Sep-17.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {


    public List<DetailModel> detailModels;
    public Context context;

    public RecycleAdapter(List<DetailModel> detailModels, Context context) {
        this.detailModels = detailModels;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public RecyclerView recyclerView;
        public CardView cardView;
        public TextView txtusername,txtemail,txtidentity,txtcontact;

        public ViewHolder(View itemView) {
            super(itemView);

            recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerview);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            txtusername = (TextView)itemView.findViewById(R.id.txtname);
            txtemail = (TextView)itemView.findViewById(R.id.txtemail);
            txtidentity = (TextView)itemView.findViewById(R.id.txtidentity);
           // txtcontact = (TextView)itemView.findViewById(R.id.txtcontact);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DetailModel detailModel = detailModels.get(position);
        holder.txtusername.setText(detailModel.getName());
        holder.txtemail.setText(detailModel.getEmail());
//        holder.txtcontact.setText(detailModel.getContact());
        holder.txtidentity.setText(detailModel.getIdentity());
    }

    @Override
    public int getItemCount() {
        return detailModels.size();
    }
}
