package msku.ceng3545.hobbyplanner.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.adapters.EventAdapter;
import msku.ceng3545.hobbyplanner.models.EventModel;

public class HomeFragment extends Fragment {

    RecyclerView rvEvents;
    EventAdapter eventAdapter;
    List<EventModel> eventList;

    public HomeFragment() {
        // Boş kurucu metod gerekli
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // XML tasarımını bağlıyoruz (fragment_home.xml)
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. RecyclerView'ı bul
        rvEvents = view.findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        // 2. SAHTE VERİLERİ OLUŞTUR (Mock Data)
        eventList = new ArrayList<>();
        eventList.add(new EventModel("Bahar Şenliği Konseri", "📍 Kampüs Stadyumu | 📅 15.05.2025", "450 / 1000 Katılımcı"));
        eventList.add(new EventModel("Python ile Yapay Zeka", "📍 Bilgisayar Müh. Lab | 📅 20.04.2025", "20 / 30 Katılımcı"));
        eventList.add(new EventModel("Doğa Yürüyüşü", "📍 Belgrad Ormanı | 📅 25.04.2025", "12 / 50 Katılımcı"));

        // 3. Adaptörü hazırla ve bağla
        eventAdapter = new EventAdapter(eventList);
        rvEvents.setAdapter(eventAdapter);

        return view;
    }
}