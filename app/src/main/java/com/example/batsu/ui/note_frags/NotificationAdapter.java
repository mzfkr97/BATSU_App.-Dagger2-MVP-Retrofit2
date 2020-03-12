package com.example.batsu.ui.note_frags;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batsu.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private List<Notification> notificationList;

    NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.title.setText(notificationList.get(position).getTitle());
        holder.first_lesson.setText(notificationList.get(position).getFirst_lesson());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, first_lesson;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            first_lesson = itemView.findViewById(R.id.first_lesson);
        }

    }

}
