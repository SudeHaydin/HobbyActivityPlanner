package msku.ceng3545.hobbyplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    List<EventModel> fullList; // Orijinal tam liste
    EditText etSearch;

    // Kategori Butonları
    Button btnCatAll, btnCatSport, btnCatArt, btnCatTech, btnCatMusic;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        // Tanımlamalar
        etSearch = view.findViewById(R.id.etSearch);
        rvDiscover = view.findViewById(R.id.rvDiscover);

        btnCatAll = view.findViewById(R.id.btnCatAll);
        btnCatSport = view.findViewById(R.id.btnCatSport);
        btnCatArt = view.findViewById(R.id.btnCatArt);
        btnCatTech = view.findViewById(R.id.btnCatTech);
        btnCatMusic = view.findViewById(R.id.btnCatMusic);

        rvDiscover.setLayoutManager(new LinearLayoutManager(getContext()));
        fullList = new ArrayList<>();
        eventAdapter = new EventAdapter(fullList);
        rvDiscover.setAdapter(eventAdapter);

        // 1. Tıklama Olayları (Filtreleme)
        btnCatAll.setOnClickListener(v -> filterCategory("Tümü", btnCatAll));
        btnCatSport.setOnClickListener(v -> filterCategory("Spor", btnCatSport));
        btnCatArt.setOnClickListener(v -> filterCategory("Sanat", btnCatArt));
        btnCatTech.setOnClickListener(v -> filterCategory("Teknoloji", btnCatTech));
        btnCatMusic.setOnClickListener(v -> filterCategory("Müzik", btnCatMusic));

        // 2. Arama Kutusu Mantığı
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                searchFilter(s.toString());
            }
        });

        // NOT: Veri çekmeyi onResume içine koyduk, buradaki fetch'i silebiliriz veya kalabilir.
        // Ama en garantisi onResume'dur.

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Sayfaya her geri dönüldüğünde (veya ilk açılışta) verileri çeker
        fetchEventsFromFirebase();
    }

    // --- FİLTRELEME İŞİ BURADA YAPILIR (Senin karıştırdığın yer burasıydı) ---
    private void filterCategory(String category, Button selectedBtn) {
        // Görsel efekt: Seçili butonu Mavi yap
        resetButtonColors();
        selectedBtn.setBackgroundColor(Color.parseColor("#2196F3"));
        selectedBtn.setTextColor(Color.WHITE);

        List<EventModel> filteredList = new ArrayList<>();

        if (category.equals("Tümü")) {
            filteredList.addAll(fullList);
        } else {
            for (EventModel item : fullList) {
                // Güvenli Kategori Kontrolü (Boşlukları silip kontrol eder)
                if (item.getCategory() != null &&
                        item.getCategory().trim().equalsIgnoreCase(category.trim())) {
                    filteredList.add(item);
                }
            }
        }
        eventAdapter.setFilteredList(filteredList);
    }

    private void searchFilter(String text) {
        List<EventModel> filteredList = new ArrayList<>();
        for (EventModel item : fullList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        eventAdapter.setFilteredList(filteredList);
    }

    // --- VERİ ÇEKME İŞİ BURADA YAPILIR (Burada if-filter olmaz) ---
    private void fetchEventsFromFirebase() {
        FirebaseFirestore.getInstance().collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    fullList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String title = document.getString("title");
                        String details = document.getString("details");

                        // Kategoriyi al (Boşsa varsayılan ata)
                        String rawCat = document.getString("category");
                        String category = (rawCat != null) ? rawCat : "community";

                        Long currentL = document.getLong("current");
                        Long maxL = document.getLong("max");
                        int current = (currentL != null) ? currentL.intValue() : 0;
                        int max = (maxL != null) ? maxL.intValue() : 0;

                        EventModel model = new EventModel(title, details, category, current, max);
                        model.setDocId(document.getId());
                        fullList.add(model);
                    }
                    // Veri geldi, adaptörü uyar
                    eventAdapter.notifyDataSetChanged();

                    // Veriler yenilenince buton rengini sıfırla ("Tümü" seçili olsun)
                    // İstersen burayı açabilirsin:
                    // resetButtonColors();
                    // btnCatAll.setBackgroundColor(Color.parseColor("#2196F3"));
                    // btnCatAll.setTextColor(Color.WHITE);
                });
    }

    private void resetButtonColors() {
        int grayColor = Color.parseColor("#CCCCCC");
        btnCatAll.setBackgroundColor(grayColor);
        btnCatSport.setBackgroundColor(grayColor);
        btnCatArt.setBackgroundColor(grayColor);
        btnCatTech.setBackgroundColor(grayColor);
        btnCatMusic.setBackgroundColor(grayColor);

        btnCatAll.setTextColor(Color.BLACK);
        btnCatSport.setTextColor(Color.BLACK);
        btnCatArt.setTextColor(Color.BLACK);
        btnCatTech.setTextColor(Color.BLACK);
        btnCatMusic.setTextColor(Color.BLACK);
    }
}