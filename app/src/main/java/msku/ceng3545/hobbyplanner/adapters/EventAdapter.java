package msku.ceng3545.hobbyplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.models.EventModel;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    // Listemiz
    private List<EventModel> eventList;

    // Constructor (Kurucu)
    public EventAdapter(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    // --- İŞTE BU EKSİKTİ! BU METODU EKLEYİNCE KIRMIZI HATALAR GİDECEK ---
    public void setFilteredList(List<EventModel> filteredList) {
        this.eventList = filteredList;
        notifyDataSetChanged();
    }
    // ---------------------------------------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel event = eventList.get(position);

        holder.tvTitle.setText(event.getTitle());
        holder.tvDetails.setText(event.getDetails());
        holder.tvStatus.setText(event.getStatusText());

        holder.itemView.setOnClickListener(v -> {
            // 1. Önce telefona bak: Zaten katılmış mı?
            android.content.SharedPreferences sharedPref = holder.itemView.getContext()
                    .getSharedPreferences("JoinedEvents", android.content.Context.MODE_PRIVATE);

            boolean isJoined = sharedPref.getBoolean(event.getDocId(), false);

            if (isJoined) {
                Toast.makeText(holder.itemView.getContext(), "Zaten katıldınız!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Kontenjan kontrolü
            if (event.getCurrent() < event.getMax()) {
                int newCurrent = event.getCurrent() + 1;

                // 3. Firebase Güncelleme
                FirebaseFirestore.getInstance()
                        .collection("events")
                        .document(event.getDocId())
                        .update("current", newCurrent)
                        .addOnSuccessListener(aVoid -> {

                            // Ekranda güncel veriyi göster
                            event.setCurrent(newCurrent);
                            holder.tvStatus.setText(event.getStatusText());

                            // Telefona Kaydet
                            android.content.SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(event.getDocId(), true);
                            editor.apply();

                            Toast.makeText(holder.itemView.getContext(), "Katıldınız! (+1)", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.itemView.getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(holder.itemView.getContext(), "Kontenjan Dolu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDetails, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvEventName);     // XML'deki ID ile aynı olmalı
            tvDetails = itemView.findViewById(R.id.tvEventDetails); // XML'deki ID ile aynı olmalı
            tvStatus = itemView.findViewById(R.id.tvEventStatus);   // XML'deki ID ile aynı olmalı
        }
    }
}