package com.example.batsu.ui.note_frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batsu.R;
import com.example.batsu.utils.JSONReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment  {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationArrayList = new ArrayList<>();

    private JSONReader jsonReader= new JSONReader();
    private InputStream inputStream;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        setUpRecyclerView(view);
        notificationArrayList.clear();

        TextView description_header = view.findViewById(R.id.description_header) ;
        description_header.setText(getString(R.string.description_header_notify));

        inputStream = getResources().openRawResource(R.raw.ring_lessons);
        notificationArrayList = jsonReader.createListFromJson(inputStream);
        adapter = new NotificationAdapter(notificationArrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

}