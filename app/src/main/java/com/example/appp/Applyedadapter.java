package com.example.appp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Applyedadapter extends RecyclerView.Adapter<Applyedadapter.ApplyedViewHolder> {

    private ArrayList<ApplyedData> ApplyedarrayList;


    public Applyedadapter(ArrayList<ApplyedData> arrayList) {
        this.ApplyedarrayList = arrayList;
    }

    @NonNull
    @Override
    public ApplyedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_applyitem_list1,parent,false);
        ApplyedViewHolder holder = new ApplyedViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplyedViewHolder holder, int position) {

        ApplyedData Image = ApplyedarrayList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Image.getIm_profileimage())
                .override(158,75)
                .into(holder.item_profile);


        holder.item_menu.setImageResource(ApplyedarrayList.get(position).getMenu());
        holder.item_title.setText(ApplyedarrayList.get(position).getTitle());
        holder.item_date.setText(ApplyedarrayList.get(position).getDate());
        holder.item_money.setText(ApplyedarrayList.get(position).getMoney());
        holder.item_address.setText(ApplyedarrayList.get(position).getAddress());


        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (null != ApplyedarrayList ? ApplyedarrayList.size() :0);
    }

    public class ApplyedViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_profile, item_menu ;
        private TextView item_title,item_date,item_money,item_address;


        public ApplyedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_profile = (ImageView)itemView.findViewById(R.id.item_profile);
            this.item_menu = (ImageView)itemView.findViewById(R.id.item_menu);

            this.item_title = (TextView)itemView.findViewById(R.id.item_title);
            this.item_date = (TextView)itemView.findViewById(R.id.item_date);
            this.item_money = (TextView)itemView.findViewById(R.id.item_money);
            this.item_address = (TextView)itemView.findViewById(R.id.item_address);



        }
    }
}
