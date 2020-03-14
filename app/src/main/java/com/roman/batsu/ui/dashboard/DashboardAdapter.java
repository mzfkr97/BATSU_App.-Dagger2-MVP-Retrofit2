package com.roman.batsu.ui.dashboard;

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
import com.roman.batsu.ui.dashboard.ResponceDashboard.ResponseDashboard;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<ResponseDashboard> dashboardList;

    private static DashboardItemClick onClickPopup;

    private int[] myDraw = {
            R.drawable.a_arrow,
            R.drawable.a_books,
            R.drawable.a_calculator,
            R.drawable.a_hands,
            R.drawable.a_hourglass,
            R.drawable.a_landscape,
            R.drawable.a_money,
            R.drawable.a_stock
    };



    DashboardAdapter(List<ResponseDashboard> dashboardList) {
        this.dashboardList = dashboardList;
    }

    @NonNull
    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new DashboardAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        String web = dashboardList.get(position).getWeb_link();

        int color_hex = dashboardList.get(position).getHex();
        holder.title.setText(dashboardList.get(position).getTitle());

        holder.title.setTextColor(myColor(color_hex));
        holder.description.setText(dashboardList.get(position).getDescription());
        if(web.isEmpty()){
            holder.web_link.setVisibility(View.GONE);
        }

        holder.web_link.setText(dashboardList.get(position).getWeb_link());
        holder.time.setText(dashboardList.get(position).getTime());
        Picasso.get().load(getRandom(myDraw)).into(holder.imageView);

    }

    private static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private int myColor(int color){
        if (color == 1){
            return Color.WHITE;
        } else if (color == 2){
            return Color.YELLOW;
        } else if (color == 3){
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView title, textShare,
                description,web_link , time;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            textShare = itemView.findViewById(R.id.textShare);
            description = itemView.findViewById(R.id.description);
            web_link = itemView.findViewById(R.id.web_link);
            time = itemView.findViewById(R.id.time);
            textShare.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (DashboardAdapter.onClickPopup != null) {
                DashboardAdapter.onClickPopup
                        .onClickPopup(dashboardList.get(getAdapterPosition()), view);
            }
        }
    }

    void setAutoOnItemClickListener(DashboardItemClick popupItemClick) {
        DashboardAdapter.onClickPopup = popupItemClick;
    }

    interface DashboardItemClick {
        /** клик в авто фрагменте
         *  private static PopupItemClick onAutoItemClickListener;
         *
         * */

        void onClickPopup(ResponseDashboard item, View view);

    }

}
