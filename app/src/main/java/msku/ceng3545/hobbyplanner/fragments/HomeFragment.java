package msku.ceng3545.hobbyplanner.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.adapters.EventAdapter;
import msku.ceng3545.hobbyplanner.adapters.HobbyAdapter;
import msku.ceng3545.hobbyplanner.models.EventModel;
import msku.ceng3545.hobbyplanner.models.HobbyModel;

public class HomeFragment extends Fragment {

    RecyclerView rvHobbies, rvEvents;
    HobbyAdapter hobbyAdapter;
    EventAdapter eventAdapter;
    List<HobbyModel> hobbyList;
    List<EventModel> eventList;

    // Firebase Veritabanı Tanımı
    FirebaseFirestore db;

    public HomeFragment() {
        // Boş kurucu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // --- 1. HOBİLER (Burası Sabit Kalabilir veya İleride Burası da Çekilebilir) ---
        rvHobbies = view.findViewById(R.id.rvHobbies);
        rvHobbies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hobbyList = new ArrayList<>();
        hobbyList.add(new HobbyModel("Yoga", 60));
        hobbyList.add(new HobbyModel("Resim", 25));
        hobbyList.add(new HobbyModel("Kitap Okuma", 80));
        hobbyList.add(new HobbyModel("Egzersiz", 10));
        hobbyAdapter = new HobbyAdapter(hobbyList);
        rvHobbies.setAdapter(hobbyAdapter);

        // --- 2. ETKİNLİKLER (ARTIK FIREBASE'DEN GELİYOR) ---
        rvEvents = view.findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        rvEvents.setAdapter(eventAdapter);

        // Veritabanını Başlat
        db = FirebaseFirestore.getInstance();

        // Verileri Çek
        fetchEventsFromFirebase();

        return view;
    }

    // HomeFragment.java içindeki metodun içi:

    private void fetchEventsFromFirebase() {
        db.collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    eventList.clear();
                    for (com.google.firebase.firestore.QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        String title = document.getString("title");
                        String details = document.getString("details");
                        String category = document.getString("category");

                        // SAYILARI ÇEKME (Firestore sayıları Long olarak verir, int'e çeviriyoruz)
                        Long currentL = document.getLong("current");
                        Long maxL = document.getLong("max");

                        int current = (currentL != null) ? currentL.intValue() : 0;
                        int max = (maxL != null) ? maxL.intValue() : 0;

                        EventModel model = new EventModel(
                                title != null ? title : "İsimsiz",
                                details != null ? details : "",
                                category != null ? category : "city",
                                current,
                                max
                        );

                        // ID'yi kaydetmeyi unutma!
                        model.setDocId(document.getId());

                        eventList.add(model);
                    }
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}