package com.example.appp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Personlistadapter extends RecyclerView.Adapter<Personlistadapter.PersonViewHolder> {

    private ArrayList<Personlistdata> PersonarrayList;


    public Personlistadapter(ArrayList<Personlistdata> arrayList) {
        this.PersonarrayList = arrayList;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_personlist_item,parent,false);
        PersonViewHolder holder = new PersonViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, final int position) {

        Personlistdata Image = PersonarrayList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Image.getIm_profileimage())
                .override(158,75)
                .into(holder.im_profileimage);


        holder.menu.setImageResource(PersonarrayList.get(position).getMenu());
        holder.name.setText(PersonarrayList.get(position).getName());
        holder.instrument.setText(PersonarrayList.get(position).getInstrument());
        //   holder.itme_arbtime.setText(HomearrayList.get(position).getArbtime());


        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Profilechek.class);

                String Name = PersonarrayList.get(position).getName();
//
//                String Money = HomearrayList.get(position).getMoney();
//                String Address = HomearrayList.get(position).getAddress();
//                String Time = HomearrayList.get(position).getArbtime();
//                String Need = HomearrayList.get(position).getContents();
//                String mypicture1 = jsonObject.getString("mypicture");

                intent.putExtra("Name", Name);

                view.getContext().startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != PersonarrayList ? PersonarrayList.size() :0);
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder {

        /////홀더와 아이템 리스트에 있는 것들을 선언해주고 연결

        private ImageView im_profileimage ,menu ;
        private TextView name,instrument;



        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.im_profileimage = (ImageView)itemView.findViewById(R.id.item_profile);
                      this.menu = (ImageView)itemView.findViewById(R.id.item_menu);

            this.name = (TextView)itemView.findViewById(R.id.item_name);
            this.instrument = (TextView)itemView.findViewById(R.id.item_instrument);




        }
    }
}
