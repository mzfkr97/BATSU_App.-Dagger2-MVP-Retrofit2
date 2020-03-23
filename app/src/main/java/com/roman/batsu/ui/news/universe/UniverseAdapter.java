package com.roman.batsu.ui.news.universe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roman.batsu.R;
import com.roman.batsu.ui.model.UniverseNews;
import com.roman.batsu.utils.ToolbarDataSetter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class UniverseAdapter extends RecyclerView.Adapter<UniverseAdapter.VievHolder> {
    List<UniverseNews> universeNewsList;
    private int[] myDraw = {
            R.drawable.a_arrow,
            R.drawable.a_work,
            R.drawable.a_boo,
            R.drawable.a_books,
            R.drawable.a_calculator,
            R.drawable.a_hands,
            R.drawable.a_hourglass,
            R.drawable.a_stock
    };

    public UniverseAdapter(List<UniverseNews> universeNewsList) {
        this.universeNewsList = universeNewsList;
    }



    @NonNull
    @Override
    public VievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new UniverseAdapter.VievHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VievHolder holder, int position) {

        String img = universeNewsList.get(position).getImage();
        setImageFromPicasso(holder, img);

        holder.title.setTextColor(Color.YELLOW);

        holder.title.setText(universeNewsList.get(position).getTitle());
        holder.description.setText(universeNewsList.get(position).getDescription());
        String author = universeNewsList.get(position).getAuthor();
        String data = new ToolbarDataSetter().dateFormatter(universeNewsList.get(position).getData());


        holder.data.setText(author + "\n" +data);

        holder.web_link.setText(universeNewsList.get(position).getWeb_link());
    }

    private void setImageFromPicasso(VievHolder holder, String image_url){
        if(image_url!=null && !image_url.isEmpty()){
            Picasso.get()
                    .load(image_url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.a_arrow)
                    .into(holder.imageView);
        }else {
            Picasso.get()
                    .load(getRandomImageInArray(myDraw))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
        }
    }

    private static int getRandomImageInArray(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    @Override
    public int getItemCount() {
        return universeNewsList.size();
    }

    public class VievHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView web_link;
        private TextView data;

        private ImageView imageView;

        public VievHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            TextView textShare = itemView.findViewById(R.id.textShare);
            textShare.setVisibility(View.GONE);
            description = itemView.findViewById(R.id.description);
            data = itemView.findViewById(R.id.time);
            web_link = itemView.findViewById(R.id.web_link);

            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
