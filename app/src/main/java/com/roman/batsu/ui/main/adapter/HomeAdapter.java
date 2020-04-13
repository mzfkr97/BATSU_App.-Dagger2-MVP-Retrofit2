package com.roman.batsu.ui.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.Home;
import com.roman.batsu.utils.ColorMaker;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<Home> responseList;
    private Context mContext;

    public HomeAdapter(Context mContext, List<Home> responseList) {
        this.mContext = mContext;
        this.responseList = responseList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textDrop, title, dateText, textFirstLess, textSecondLesson, textThreeLesson, textFourLesson;
        private TextView countFirstLesson, countSecondLesson,
                countThreeLesson, countFourLesson;

        MyViewHolder(@NonNull View view) {
            super(view);
            textDrop = view.findViewById(R.id.textDrop);
            title = view.findViewById(R.id.title);
            dateText = view.findViewById(R.id.dateText);
            textFirstLess = view.findViewById(R.id.textFirstLess);
            textSecondLesson = view.findViewById(R.id.textSecondLesson);
            textThreeLesson = view.findViewById(R.id.textThreeLesson);
            textFourLesson = view.findViewById(R.id.textFourLesson);
            countFirstLesson = view.findViewById(R.id.countFirstLesson);
            countSecondLesson = view.findViewById(R.id.countSecondLesson);
            countThreeLesson = view.findViewById(R.id.countThreeLesson);
            countFourLesson = view.findViewById(R.id.countFourLesson);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(responseList.get(position).getDay());
        holder.dateText.setText(responseList.get(position).getDate());
        holder.textDrop.setTextColor(new ColorMaker(mContext).getRandomMaterialColor("400"));
        String empty =  "size =   " + responseList.get(position).getLessons().size();

        try{
            holder.countFirstLesson.setText(responseList.get(position).getLessons().get(0).getCount());
            holder.textFirstLess.setText(responseList.get(position).getLessons().get(0).getLesson_name());

            holder.countSecondLesson.setText(responseList.get(position).getLessons().get(1).getCount());
            holder.textSecondLesson.setText(responseList.get(position).getLessons().get(1).getLesson_name());

            holder.countThreeLesson.setText(responseList.get(position).getLessons().get(2).getCount());
            holder.textThreeLesson.setText(responseList.get(position).getLessons().get(2).getLesson_name());

            holder.countFourLesson.setText(responseList.get(position).getLessons().get(3).getCount());
            holder.textFourLesson.setText(responseList.get(position).getLessons().get(3).getLesson_name());


        }catch (Exception e){
            Log.d("TAG", "onBindViewHolder: " + e + empty);
        }

    }


    @Override
    public int getItemCount() {
        return responseList.size();
    }

}