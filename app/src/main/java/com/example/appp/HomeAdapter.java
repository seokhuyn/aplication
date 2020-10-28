package com.example.appp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.appp.Home.REQUEST_CODE2;
import static com.example.appp.Login.MYid;
import static com.example.appp.Myschedule.REQUEST_CODE1;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Homeviewholder> implements Filterable {





    private ArrayList<HomeData> HomearrayList;
    private ArrayList<HomeData> HomearrayListfull;

    public HomeAdapter(ArrayList<HomeData> arrayList) {
        this.HomearrayList = arrayList;
        HomearrayListfull = HomearrayList;

        Log.d("filter", "홈어댑터 ");

    }

    public class Homeviewholder extends RecyclerView.ViewHolder {

        private ImageView item_profile, item_menu;
        private TextView item_title, item_date, item_money, item_address, itme_arbtime;

        public Homeviewholder(@NonNull View itemView) {
            super(itemView);
            this.item_profile = (ImageView) itemView.findViewById(R.id.item_profile);
            this.item_menu = (ImageView) itemView.findViewById(R.id.item_menu);

            this.item_title = (TextView) itemView.findViewById(R.id.item_title);
            this.item_date = (TextView) itemView.findViewById(R.id.item_date);
            this.item_money = (TextView) itemView.findViewById(R.id.item_money);
            this.item_address = (TextView) itemView.findViewById(R.id.item_address);
            // this.itme_arbtime = (TextView)itemView.findViewById(R.id.itme_arbtime);

        }

    }

    @NonNull
    @Override
    public Homeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list, parent, false);
        Homeviewholder holder = new Homeviewholder(view);

        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull final Homeviewholder holder, final int position) {

        Log.d("shared", "홈어댑터" + HomearrayList.get(position).getAddress());
        Log.d("shared", "홈어댑터" + HomearrayListfull.get(position).getAddress());


        HomeData Image = HomearrayList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(Image.getIm_profileimage())
                .override(158, 75)
                .into(holder.item_profile);


        holder.item_menu.setImageResource(HomearrayList.get(position).getMenu());
        holder.item_title.setText(HomearrayList.get(position).getTitle());
        holder.item_date.setText(HomearrayList.get(position).getDate());
        holder.item_money.setText(HomearrayList.get(position).getMoney());
        holder.item_address.setText(HomearrayList.get(position).getAddress());
        //   holder.itme_arbtime.setText(HomearrayList.get(position).getArbtime());


        holder.itemView.setTag(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("shared", "홈어댑터 터치");

                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("Userannouncedata", MODE_PRIVATE);
                String json = sharedPreferences.getString("announce", "");

                try {
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        Log.d("shared", "아이디" + id);


                        if (MYid.equals(id)) {
                            String mypicture = jsonObject.getString("mypicture");
                            if (HomearrayList.get(position).getIm_profileimage().equals(mypicture)) {


                                Intent intent = new Intent(view.getContext(), Announcecheck.class);
                                Log.d("shared", "홈어댑터 아이디맞다");
                                String Title = HomearrayList.get(position).getTitle();
                                String Date = HomearrayList.get(position).getDate();
                                String Money = HomearrayList.get(position).getMoney();
                                String Address = HomearrayList.get(position).getAddress();
                                String Time = HomearrayList.get(position).getArbtime();
                                String Need = HomearrayList.get(position).getContents();
                                String mypicture1 = jsonObject.getString("mypicture");

                                intent.putExtra("Title", Title);
                                intent.putExtra("Date", Date);
                                intent.putExtra("Money", Money);
                                intent.putExtra("Address", Address);
                                intent.putExtra("Time", Time);
                                intent.putExtra("Need", Need);
                                intent.putExtra("mypicture", mypicture1);
                                view.getContext().startActivity(intent);

                                return;
                            } else {


                            }
                        } else {


                        }
                    }

//                    Log.d("shared", "아이디 결과" + id);
                    Intent intent = new Intent(view.getContext(), Announceapply.class);
                    Log.d("shared", "홈어댑터 아이디 다르다");
                    String Title = HomearrayList.get(position).getTitle();
                    String Date = HomearrayList.get(position).getDate();
                    String Money = HomearrayList.get(position).getMoney();
                    String Address = HomearrayList.get(position).getAddress();
                    String Time = HomearrayList.get(position).getArbtime();
                    String Need = HomearrayList.get(position).getContents();
                    //  String mypicture = HomearrayList.get(position).getIm_profileimage();


                    intent.putExtra("Title", Title);
                    intent.putExtra("Date", Date);
                    intent.putExtra("Money", Money);
                    intent.putExtra("Address", Address);
                    intent.putExtra("Time", Time);
                    intent.putExtra("Need", Need);
                    //   intent.putExtra("mypicture", mypicture);


                    view.getContext().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


                                Intent intent = new Intent(((Activity) view.getContext()), Announceedit22.class);


                                String Title = HomearrayList.get(position).getTitle();
                                String Date = HomearrayList.get(position).getDate();
                                String Money = HomearrayList.get(position).getMoney();
                                String Address = HomearrayList.get(position).getAddress();
                                String Need = HomearrayList.get(position).getContents();
                                String Time = HomearrayList.get(position).getArbtime();

                                int Position = holder.getAdapterPosition();


                                Log.d("shared", "수정" + HomearrayList.get(position).getDate());
                                Log.d("shared", "수정" + HomearrayList.get(position).getTitle());
                                Log.d("shared", "수정" + HomearrayList.get(position).getAddress());
                                Log.d("shared", "수정" + HomearrayList.get(position).getMoney());
                                Log.d("shared", "수정" + HomearrayList.get(position).getContents());
                                Log.d("shared", "수정" + HomearrayList.get(position).getArbtime());


                                intent.putExtra("Title", Title);
                                intent.putExtra("Date", Date);
                                intent.putExtra("Money", Money);
                                intent.putExtra("Address", Address);
                                intent.putExtra("Position", Position);
                                intent.putExtra("Need", Need);
                                intent.putExtra("Time", Time);


                                ((Activity) view.getContext()).startActivityForResult(intent, REQUEST_CODE2);


                                break;

                            case R.id.popup_delete:


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

                                            if (getTitle.equals(HomearrayList.get(position).getTitle())) {
                                                Log.d("shared1", " 제목" + (HomearrayList.get(position).getTitle()));
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


                                                Log.d("shared", "저장하자" + jsonArray.toString());

                                                editor.putString("announce", jsonArray.toString());
                                                editor.commit();

                                                HomearrayList.remove(position);
                                                notifyItemRemoved(position);
                                                notifyDataSetChanged();


                                                break;

                                            } else {

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
        return (null != HomearrayList ? HomearrayList.size() : 0);
    }


    @Override
    public Filter getFilter() {
        return Homefilter;
    }


    private Filter Homefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            Log.d("Home", "filter charSeqence " + charSequence);

                    ArrayList<HomeData> filteredlist = new ArrayList<>();


                    if (charSequence == null || charSequence.length() == 0) {
                        filteredlist.addAll(HomearrayListfull);
                    } else {
                        String filteredPattern = charSequence.toString().toLowerCase().trim();

                        for (HomeData data : HomearrayListfull) {
                            if (data.getTitle().toLowerCase().contains(filteredPattern) ||data.getAddress().toLowerCase().contains(filteredPattern)
                            ||data.getDate().toLowerCase().contains(filteredPattern) ||data.getMoney().toLowerCase().contains(filteredPattern)||
                            data.getContents().toLowerCase().contains(filteredPattern)) {
                                Log.d("Home", "filter data.getTitle " + data.getTitle());

                                filteredlist.add(data);
                            } else {

                            }
                        }
                    }


                    FilterResults results = new FilterResults();
                    results.values = filteredlist;
                    Log.d("Home", "filter" + results.values.toString());

                    return results;


        }


            @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        HomearrayList.clear();
        HomearrayList.addAll((ArrayList) filterResults.values);
        notifyDataSetChanged();
        }
    };
}
