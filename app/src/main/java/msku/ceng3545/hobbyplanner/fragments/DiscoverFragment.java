package msku.ceng3545.hobbyplanner.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import msku.ceng3545.hobbyplanner.models.EventModel;

public class DiscoverFragment extends Fragment {

    RecyclerView rvDiscover;
    EventAdapter eventAdapter;
    List<EventModel> fullList; // Firebase'den gelen tüm liste
    EditText etSearch;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        rvDiscover = view.findViewById(R.id.rvDiscover);
        rvDiscover.setLayoutManager(new LinearLayoutManager(getContext()));

        fullList = new ArrayList<>();
        // Adaptöre başlangıçta boş liste veriyoruz
        eventAdapter = new EventAdapter(fullList);
        rvDiscover.setAdapter(eventAdapter);

        // ARAMA FONKSİYONU
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // FIREBASE'DEN VERİLERİ ÇEK
        fetchEventsFromFirebase();

        return view;
    }

    private void fetchEventsFromFirebase() {
        FirebaseFirestore.getInstance().collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    fullList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        // Verileri güvenli şekilde çek
                        String title = document.getString("title");
                        String details = document.getString("details");
                        String category = document.getString("category");

                        Long currentL = document.getLong("current");
                        Long maxL = document.getLong("max");
                        int current = (currentL != null) ? currentL.intValue() : 0;
                        int max = (maxL != null) ? maxL.intValue() : 0;

                        EventModel model = new EventModel(
                                title != null ? title : "",
                                details != null ? details : "",
                                category != null ? category : "city",
                                current,
                                max
                        );

                        // ID ATAMASI (Çökmemesi için en önemli yer)
                        model.setDocId(document.getId());

                        fullList.add(model);
                    }
                    // Veri geldi, listeyi güncelle
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Veri alınamadı", Toast.LENGTH_SHORT).show();
                });
    }

    private void filter(String text) {
        List<EventModel> filteredList = new ArrayList<>();
        for (EventModel item : fullList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        eventAdapter.setFilteredList(filteredList);
    }
}