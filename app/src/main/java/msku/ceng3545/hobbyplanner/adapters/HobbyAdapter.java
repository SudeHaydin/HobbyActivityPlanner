//SUDE HAYDIN

package msku.ceng3545.hobbyplanner.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.models.HobbyModel;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.HobbyViewHolder> {

    private List<HobbyModel> hobbyList;

    public HobbyAdapter(List<HobbyModel> hobbyList) {
        this.hobbyList = hobbyList;
    }

    @NonNull
    @Override
    public HobbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hobby_card, parent, false);
        return new HobbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HobbyViewHolder holder, int position) {
        HobbyModel hobby = hobbyList.get(position);

        holder.tvName.setText(hobby.getName());
        holder.progressBar.setProgress(hobby.getPercent());
        holder.tvPercent.setText("%" + hobby.getPercent());

        // --- RENK VE İKON AYARLAMA ---
        String colorCode = "#FFFFFF"; // Varsayılan Beyaz

        switch (hobby.getName()) {
            case "Yoga":
                holder.imgIcon.setImageResource(R.drawable.ic_yoga);
                colorCode = "#E1BEE7";
                break;
            case "Resim":
                holder.imgIcon.setImageResource(R.drawable.ic_art);
                colorCode = "#FFF9C4";
                break;
            case "Kitap Okuma":
                holder.imgIcon.setImageResource(R.drawable.ic_book);
                colorCode = "#C8E6C9";
                break;
            case "Egzersiz":
                holder.imgIcon.setImageResource(R.drawable.ic_fitness);
                colorCode = "#BBDEFB";
                break;
            default:
                holder.imgIcon.setImageResource(R.drawable.ic_launcher_foreground);
                colorCode = "#F5F5F5";
                break;
        }


        holder.cardView.setCardBackgroundColor(Color.parseColor(colorCode));


        holder.itemView.setOnClickListener(v -> {
            int currentPercent = hobby.getPercent();
            if (currentPercent < 100) {
                int newPercent = currentPercent + 10;
                if (newPercent > 100) newPercent = 100;
                hobby.setPercent(newPercent);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }

    public static class HobbyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPercent;
        ProgressBar progressBar;
        ImageView imgIcon;
        CardView cardView;

        public HobbyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHobbyName);
            progressBar = itemView.findViewById(R.id.progressBarHobby);
            tvPercent = itemView.findViewById(R.id.tvProgressText);
            imgIcon = itemView.findViewById(R.id.imgHobbyIcon);


            cardView = itemView.findViewById(R.id.cardViewHobby);
        }
    }
}