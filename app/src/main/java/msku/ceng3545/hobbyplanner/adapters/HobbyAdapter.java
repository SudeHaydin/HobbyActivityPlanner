package msku.ceng3545.hobbyplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
        holder.progressBar.setProgress(hobby.getProgress());
        holder.tvPercent.setText("%" + hobby.getProgress());
    }

    @Override
    public int getItemCount() {
        return hobbyList.size();
    }

    public static class HobbyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPercent;
        ProgressBar progressBar;

        public HobbyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHobbyName);
            progressBar = itemView.findViewById(R.id.pbHobbyProgress);
            tvPercent = itemView.findViewById(R.id.tvProgressPercent);
        }
    }
}