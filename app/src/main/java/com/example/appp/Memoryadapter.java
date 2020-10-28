package com.example.appp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.appp.Memory.REQUEST_CODE13;


public class Memoryadapter extends   RecyclerView.Adapter<Memoryadapter.adapterViewHolder>  {

    Context context ;

     ArrayList arrayid , arraytitle , arraydate ;


    public Memoryadapter(Context context ,ArrayList arrayid, ArrayList arraytitle,ArrayList arraydate ){
        this. context = context;
        this. arrayid = arrayid;
        this. arraytitle = arraytitle;
        this. arraydate = arraydate;

    }


    @NonNull
    @Override
    public adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.memory_item,parent,false);
        return new adapterViewHolder(view) ;
    }


    @Override
    public void onBindViewHolder(@NonNull final adapterViewHolder holder, final int position) {



        holder.id.setText(String.valueOf(arrayid.get(position)));
        holder.text.setText(String.valueOf(arraytitle.get(position)));
        holder.date.setText(String.valueOf(arraydate.get(position)));



        holder.myitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(((Activity) view.getContext()),Edit.class);
                intent.putExtra("id",String.valueOf(arrayid.get(position)));
                intent.putExtra("title",String.valueOf(arraytitle.get(position)));
                intent.putExtra("date",String.valueOf(arraydate.get(position)));

                Log.d("adapter", "" + arrayid.get(position) );

                Log.d("adapter", "" + arraytitle.get(position) );

                Log.d("adapter", "" + arraydate.get(position) );


                ((Activity) view.getContext()).startActivityForResult(intent, REQUEST_CODE13);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayid.size() ;
    }



    public class adapterViewHolder extends RecyclerView.ViewHolder {
            TextView text, date ,id ;
            LinearLayout myitem ;



        public adapterViewHolder(@NonNull View itemView) {
            super(itemView);

            this.id  = (TextView) itemView.findViewById(R.id.id);
            this.text = (TextView) itemView.findViewById(R.id.text);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.myitem = itemView.findViewById(R.id.myitem);


        }
    }
}
