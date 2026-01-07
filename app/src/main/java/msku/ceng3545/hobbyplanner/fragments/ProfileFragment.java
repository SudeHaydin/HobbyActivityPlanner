package msku.ceng3545.hobbyplanner.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.adapters.EventAdapter;
import msku.ceng3545.hobbyplanner.models.EventModel;

public class ProfileFragment extends Fragment {

    RecyclerView rvHistory;
    EventAdapter adapter;
    List<EventModel> eventList;

    TextView tvProfileName, tvProfileEmail, tvCreatedCount, tvJoinedCount;
    ImageView btnHistory, btnUploads, btnFavorites;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Arayüz elemanlarını bağla
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvCreatedCount = view.findViewById(R.id.tvCreatedCount);
        tvJoinedCount = view.findViewById(R.id.tvJoinedCount);

        btnHistory = view.findViewById(R.id.btnHistory);
        btnUploads = view.findViewById(R.id.btnUploads);
        btnFavorites = view.findViewById(R.id.btnFavorites);

        rvHistory = view.findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        adapter = new EventAdapter(eventList);
        rvHistory.setAdapter(adapter);

        // --- 1. VERİTABANINDAN İSİM ÇEKME FONKSİYONU ---
        fetchUserDataFromFirebase();

        // 2. Listeyi Getir (Katıldıklarım)
        loadEvents("JoinedEvents");
        highlightButton(btnHistory);

        // Buton Tıklamaları
        btnHistory.setOnClickListener(v -> {
            loadEvents("JoinedEvents");
            highlightButton(btnHistory);
        });

        btnUploads.setOnClickListener(v -> {
            loadEvents("CreatedEvents");
            highlightButton(btnUploads);
        });

        return view;
    }

    private void fetchUserDataFromFirebase() {
        // 1. Önce telefondan "Hangi mail ile giriş yapıldı?" bilgisini al
        SharedPreferences prefs = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("userEmail", null);

        if (email != null) {
            // Ekrana maili hemen yaz
            tvProfileEmail.setText(email);

            // 2. Veritabanına git: "users" koleksiyonunda bu maile sahip kişiyi bul
            FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("email", email) // E-postası eşleşen kullanıcıyı ara
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Bulduk! İlk dokümanı al
                            String nameFromDb = queryDocumentSnapshots.getDocuments().get(0).getString("name");

                            // İsmi ekrana yaz
                            if (nameFromDb != null) {
                                tvProfileName.setText(nameFromDb);
                            } else {
                                tvProfileName.setText("İsimsiz Kullanıcı");
                            }
                        } else {
                            // Belki kullanıcı kayıt olurken 'email' alanını farklı kaydetmiş olabiliriz
                            // Eğer isim gelmezse buraya bakarız.
                            tvProfileName.setText("Kullanıcı Verisi Yok");
                        }
                    })
                    .addOnFailureListener(e -> {
                        tvProfileName.setText("Bağlantı Hatası");
                    });
        } else {
            tvProfileName.setText("Misafir");
            tvProfileEmail.setText("Giriş Yapılmadı");
        }
    }

    // ... (loadEvents ve highlightButton metodları aynı kalacak, önceki koddan kopyalayabilirsin) ...

    private void loadEvents(String mode) {
        SharedPreferences sharedPref = getContext().getSharedPreferences(mode, Context.MODE_PRIVATE);
        FirebaseFirestore.getInstance().collection("events").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String docId = document.getId();
                        if (sharedPref.getBoolean(docId, false)) {
                            String title = document.getString("title");
                            String details = document.getString("details");
                            String category = document.getString("category");
                            Long currentL = document.getLong("current");
                            Long maxL = document.getLong("max");
                            int current = (currentL != null) ? currentL.intValue() : 0;
                            int max = (maxL != null) ? maxL.intValue() : 0;
                            EventModel model = new EventModel(title, details, category, current, max);
                            model.setDocId(docId);
                            eventList.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (mode.equals("JoinedEvents")) tvJoinedCount.setText("Katılınan: " + eventList.size());
                    else if (mode.equals("CreatedEvents")) tvCreatedCount.setText("Oluşturulan: " + eventList.size());
                });
    }

    private void highlightButton(ImageView selectedBtn) {
        btnHistory.setColorFilter(Color.GRAY);
        btnUploads.setColorFilter(Color.GRAY);
        btnFavorites.setColorFilter(Color.GRAY);
        selectedBtn.setColorFilter(Color.parseColor("#E91E63"));
    }
}