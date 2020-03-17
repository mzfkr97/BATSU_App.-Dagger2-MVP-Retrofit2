package com.roman.batsu.ui.rings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.Rings;
import com.roman.batsu.utils.JSONReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RingsFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RingsAdapter adapter;
    private List<Rings> notificationArrayList = new ArrayList<>();

    private JSONReader jsonReader= new JSONReader();

    public static RingsFragment newInstance() {
        return new RingsFragment();
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        setUpRecyclerView(view);
        notificationArrayList.clear();

        TextView description_header = view.findViewById(R.id.description_header) ;
        description_header.setText(getString(R.string.description_header_notify));

        InputStream inputStream = getResources().openRawResource(R.raw.ring_lessons);
        notificationArrayList = jsonReader.createListFromJson(inputStream);
        adapter = new RingsAdapter(getActivity(), notificationArrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

}