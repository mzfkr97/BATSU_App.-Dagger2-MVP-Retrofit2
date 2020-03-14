package com.roman.batsu.ui.note_frags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.batsu.R;
import com.roman.batsu.utils.ColorMaker;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private List<Notification> notificationList;
    private Context mContext;

    NotificationAdapter(Context mContext,List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.title.setText(notificationList.get(position).getTitle());
        holder.first_lesson.setText(notificationList.get(position).getFirst_lesson());
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
