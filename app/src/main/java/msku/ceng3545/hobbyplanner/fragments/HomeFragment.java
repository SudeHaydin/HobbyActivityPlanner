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
import msku.ceng3545.hobbyplanner.adapters.HobbyAdapter;
import msku.ceng3545.hobbyplanner.models.EventModel;
import msku.ceng3545.hobbyplanner.models.HobbyModel;

public class HomeFragment extends Fragment {

    RecyclerView rvHobbies, rvEvents;

    HobbyAdapter hobbyAdapter;
    EventAdapter eventAdapter;

    List<HobbyModel> hobbyList;
    List<EventModel> eventList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHobbies = view.findViewById(R.id.rvHobbies);
        rvHobbies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        hobbyList = new ArrayList<>();
        hobbyList.add(new HobbyModel("Yoga", 60));
        hobbyList.add(new HobbyModel("Resim", 25));
        hobbyList.add(new HobbyModel("Kitap Okuma", 80));
        hobbyList.add(new HobbyModel("Gitar", 10));

        hobbyAdapter = new HobbyAdapter(hobbyList);
        rvHobbies.setAdapter(hobbyAdapter);


        rvEvents = view.findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        eventList = new ArrayList<>();
        eventList.add(new EventModel("Bahar Şenliği", "📍 Kampüs | 📅 15.05.2025", "450 / 1000 Katılımcı", "city"));
        eventList.add(new EventModel("Yapay Zeka Semineri", "📍 Lab B | 📅 20.04.2025", "20 / 30 Katılımcı", "community"));
        eventList.add(new EventModel("Doğa Yürüyüşü", "📍 Orman | 📅 25.04.2025", "12 / 50 Katılımcı", "community"));
        eventAdapter = new EventAdapter(eventList);
        rvEvents.setAdapter(eventAdapter);

        return view;
    }
}