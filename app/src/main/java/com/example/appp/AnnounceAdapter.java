package com.example.appp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.appp.Login.MYid;
import static com.example.appp.Myschedule.REQUEST_CODE1;
import static com.example.appp.Profile.userlist;


public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.AnnounceViewHolder> {


    private ArrayList<AnnounceData> arrayList;


    public AnnounceAdapter(ArrayList<AnnounceData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AnnounceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list,parent,false);
        AnnounceViewHolder holder = new AnnounceViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AnnounceViewHolder holder, final int position) {
        Log.d("shared", "공지어댑터" + arrayList.get(position).getAddress());

//      holder.item_profile.setImageURI(arrayList.get(position).getIm_profileimage());


        AnnounceData Image = arrayList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Image.getIm_profileimage())
                .override(158,75)
                .into(holder.item_profile);


        holder.item_menu.setImageResource(arrayList.get(position).getMenu());
        holder.item_title.setText(arrayList.get(position).getTitle());
        holder.item_date.setText(arrayList.get(position).getDate());
        holder.item_money.setText(arrayList.get(position).getMoney());
        holder.item_address.setText(arrayList.get(position).getAddress());
     //   holder.itme_arbtime.setText(arrayList.get(position).getArbtime());


        holder.itemView.setTag(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                            Intent intent = new Intent(view.getContext(), Announcecheck.class);

                            String Title = arrayList.get(position).getTitle();
                            String Date = arrayList.get(position).getDate();
                            String Money = arrayList.get(position).getMoney();
                            String Address = arrayList.get(position).getAddress();
       //                     String Time = arrayList.get(position).getArbtime();
                            String Need = arrayList.get(position).getContents();

                            intent.putExtra("Title", Title);
                            intent.putExtra("Date", Date);
                            intent.putExtra("Money", Money);
                            intent.putExtra("Address", Address);
               //             intent.putExtra("Time", Time);
                            intent.putExtra("Need", Need);


                            view.getContext().startActivity(intent);

                      }

                    });

        holder.item_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        holder.item_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.popup);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popup_edit:


                                Intent intent = new Intent(((Activity) view.getContext()), Announceedit.class);


                                String Title = arrayList.get(position).getTitle();
                                String Date = arrayList.get(position).getDate();
                                String Money = arrayList.get(position).getMoney();
                                String Address = arrayList.get(position).getAddress();
                                String Need = arrayList.get(position).getContents();
                                String Time = arrayList.get(position).getArbtime();

                                int Position = holder.getAdapterPosition();


                                Log.d("shared", "수정" + arrayList.get(position).getDate());
                                Log.d("shared", "수정" + arrayList.get(position).getTitle());
                                Log.d("shared", "수정" + arrayList.get(position).getAddress());
                                Log.d("shared", "수정" + arrayList.get(position).getMoney());
                                Log.d("shared", "수정" + arrayList.get(position).getContents());
                                Log.d("shared", "수정" + arrayList.get(position).getArbtime());


                                intent.putExtra("Title", Title);
                                intent.putExtra("Date", Date);
                                intent.putExtra("Money", Money);
                                intent.putExtra("Address", Address);
                                intent.putExtra("Position", Position);
                                intent.putExtra("Need", Need);
                                intent.putExtra("Time", Time);


                                ((Activity) view.getContext()).startActivityForResult(intent, REQUEST_CODE1);


                                break;

                            case R.id.popup_delete:

//
//                              arrayList.remove(position);
//                              notifyItemRemoved(position);
//                              notifyDataSetChanged();
//                              Gson gson = new Gson();
//                              String json = gson.toJson(arrayList);
//                              SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                              SharedPreferences.Editor editor = sharedPreferences.edit();
//                              editor.putString("announce", json);
//                              editor.apply();


                                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                                String json = sharedPreferences.getString("announce", "");


                                try {

                                    JSONArray jsonArray = new JSONArray(json);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                                        String id = jsonObject.getString("id");

                                        if (MYid.equals(id)) {
                                            String getTitle = jsonObject.getString("Title");
                                            Log.d("shared1", " 제목" + MYid);
                                            Log.d("shared1", " 제목" + id);

                                            if (getTitle.equals(arrayList.get(position).getTitle())) {
                                                Log.d("shared1", " 제목" + (arrayList.get(position).getTitle()));
                                                Log.d("shared1", " 제목" + getTitle);

                                                sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();


                                                String Date1 = jsonObject.getString("Date");
                                                String Title1 = jsonObject.getString("Title");
                                                String Money1 = jsonObject.getString("Money");
                                                String Need1 = jsonObject.getString("Need");
                                                String Address1 = jsonObject.getString("Address");
                                                String Time1 = jsonObject.getString("Time");
                                                String id1 = jsonObject.getString("id");
                                                String mypicture = jsonObject.getString("mypicture");

                                                jsonObject.remove(Date1);
                                                jsonObject.remove(Title1);
                                                jsonObject.remove(Money1);
                                                jsonObject.remove(Need1);
                                                jsonObject.remove(Address1);
                                                jsonObject.remove(Time1);
                                                jsonObject.remove(id1);
                                                jsonObject.remove(mypicture);
                                                Log.d("shared", "지우자" + jsonArray.remove(i));

                                                // editor.putString("announce", jsonArray.toString());
                                                Log.d("shared", "저장하자" + jsonArray.toString());


                                                arrayList.remove(position);
                                                notifyItemRemoved(position);
                                                notifyDataSetChanged();

                                                editor.putString("announce", jsonArray.toString());
                                                editor.commit();

                                                //      editor.commit();
//
//                                                       arrayList.remove(position);
//
//                                                       notifyItemChanged(position);
//                                                       notifyDataSetChanged();
//
//

//                                                       Log.d("shared", "지우자" +jsonObject.getString("Title"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("Date"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("Money"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("Address"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("Need"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("Time"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("id"));
//                                                       Log.d("shared", "지우자" +jsonObject.getString("mypicture"));

                                                break;

                                            } else {

//                                                       sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
//                                                       SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                       editor.putString("announce", jsonArray.toString());
//                                                       editor.commit();
                                                Log.d("shared1", " 다른 제목" + getTitle);
                                            }

                                        }


                                    }

                                    Log.d("shared", "saveeditor" + jsonArray.toString());
                                    Log.d("shared1", " 나왔다" + MYid);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                        }

                        return false;
                    }
                });


            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() :0);
//        return arrayList.size();
    }


//    public void remove(int position){
//        try{
//            arrayList.remove(position);
//            notifyItemRemoved(position);
//            notifyDataSetChanged();
//
//        }catch (IndexOutOfBoundsException ex){
//            ex.printStackTrace();
//        }
//    }


    public class AnnounceViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_profile,item_menu ;
        private TextView item_title,item_date,item_money,item_address,itme_arbtime ;

        public AnnounceViewHolder(@NonNull View itemView) {
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
