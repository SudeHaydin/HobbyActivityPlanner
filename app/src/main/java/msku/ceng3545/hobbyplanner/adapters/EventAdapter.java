package msku.ceng3545.hobbyplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.models.EventModel;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<EventModel> eventList;
    public EventAdapter(List<EventModel> eventList){
        this.eventList=eventList;
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_card,parent,false);
        return new EventViewHolder(view);

    }
    // EventAdapter.java dosyasının içindeki onBindViewHolder metodunu bul ve bununla değiştir:

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventModel event = eventList.get(position);

        holder.tvTitle.setText(event.getTitle());
        holder.tvDetails.setText(event.getDetails());
        holder.tvStatus.setText(event.getParticipantStatus());

        // TIKLAMA OLAYI (YENİ EKLENEN KISIM)
        holder.itemView.setOnClickListener(v -> {
            // Tıklanınca Detay Sayfasına git
            android.content.Intent intent = new android.content.Intent(holder.itemView.getContext(), msku.ceng3545.hobbyplanner.activities.EventDetailsActivity.class);

            // Verileri paketle ve gönder
            intent.putExtra("title", event.getTitle());
            intent.putExtra("details", event.getDetails());
            intent.putExtra("status", event.getParticipantStatus());

            holder.itemView.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount(){
        return eventList.size();
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvDetails, tvStatus;
        public EventViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvEventTitle);
            tvDetails=itemView.findViewById(R.id.tvEventDetails);
            tvStatus=itemView.findViewById(R.id.tvParticipantStatus);
        }
    }
}
