package com.roman.batsu.ui.rings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.Rings;
import com.roman.batsu.utils.ColorMaker;

import java.util.List;

public class RingsAdapter extends RecyclerView.Adapter<RingsAdapter.ViewHolder> {


    private List<Rings> notificationList;
    private Context mContext;

    RingsAdapter(Context mContext, List<Rings> notificationList) {
        this.notificationList = notificationList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RingsAdapter.ViewHolder holder, int position) {
        holder.title.setText(notificationList.get(position).getTitle());
        holder.first_lesson.setText(notificationList.get(position).getDescription());
        holder.textDrop.setBackgroundColor(new ColorMaker(mContext).getRandomMaterialColor("400"));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  title, first_lesson;
        private View textDrop;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            textDrop = itemView.findViewById(R.id.textDrop);
            first_lesson = itemView.findViewById(R.id.first_lesson);
        }

    }

}
