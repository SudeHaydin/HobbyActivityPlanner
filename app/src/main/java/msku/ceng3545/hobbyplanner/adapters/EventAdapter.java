package msku.ceng3545.hobbyplanner.adapters;
//MEHMET EKREM ERKAN
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

    // Kırmızı yanan 'eventList' hatası için bu tanımlama şart:
    private List<EventModel> eventList;

    // Kurucu Metot (Constructor)
    public EventAdapter(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    // Arama yaparken listeyi güncellemek için (Sorduğun diğer hata buydu)
    public void setFilteredList(List<EventModel> filteredList) {
        this.eventList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tasarımı bağla (list_item_event.xml veya benzeri bir isim olmalı)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel event = eventList.get(position);

        holder.tvTitle.setText(event.getTitle());
        holder.tvDetails.setText(event.getDetails());

        // Sayıları modelden alıp birleştiriyoruz: "10 / 50"
        holder.tvStatus.setText(event.getStatusText());

        // Tıklama Olayı (Senin yazdığın Integer mantığı)
        // EventAdapter.java -> onBindViewHolder içi

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

                            // A) Ekranda güncel veriyi göster
                            event.setCurrent(newCurrent);
                            holder.tvStatus.setText(event.getStatusText());

                            // --- B) TELEFONA KAYDET (Burası Eksikti!) ---
                            android.content.SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean(event.getDocId(), true); // "JoinedEvents" dosyasına kaydet
                            editor.apply();
                            // -------------------------------------------

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

    // --- ViewHolder Sınıfı (Kırmızı yanan tvTitle, tvDetails hataları burası eksik olduğu içindi) ---
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDetails, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // XML'deki ID'lerinle buradakilerin eşleşmesi lazım!
            // Eğer XML'de farklı isim verdiysen buraları düzeltmen gerekir.
            tvTitle = itemView.findViewById(R.id.tvEventName);
            tvDetails = itemView.findViewById(R.id.tvEventDetails);
            tvStatus = itemView.findViewById(R.id.tvEventStatus);
        }
    }
}