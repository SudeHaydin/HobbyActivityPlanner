package msku.ceng3545.hobbyplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.adapters.EventAdapter;
import msku.ceng3545.hobbyplanner.models.EventModel;

public class DiscoverFragment extends Fragment {

    RecyclerView rvDiscoverEvents;
    EventAdapter eventAdapter;
    List<EventModel> fullList;


    EditText searchBar;
    TextView tabAll, tabCity, tabCommunity;

    public DiscoverFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        rvDiscoverEvents = view.findViewById(R.id.rvDiscoverEvents);
        searchBar = view.findViewById(R.id.etSearch);
        tabAll = view.findViewById(R.id.tabAll);
        tabCity = view.findViewById(R.id.tabCity);
        tabCommunity = view.findViewById(R.id.tabCommunity);

        rvDiscoverEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        fullList = new ArrayList<>();
        fullList.add(new EventModel("Müzik Festivali", "🎶 Şehir Parkı", "67/700", "city"));
        fullList.add(new EventModel("Açık Hava Sineması", "🎬 Kampüs", "150 Katılımcı", "city"));
        fullList.add(new EventModel("Seramik Atölyesi", "🎨 Sanat Evi", "12 Katılımcı", "community"));
        fullList.add(new EventModel("Kodlama Kampı", "💻 Lab 1", "30 Katılımcı", "community"));

        eventAdapter = new EventAdapter(fullList);
        rvDiscoverEvents.setAdapter(eventAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        tabAll.setOnClickListener(v -> {
            updateTabs(tabAll);
            eventAdapter.setFilteredList(fullList);
        });

        tabCity.setOnClickListener(v -> {
            updateTabs(tabCity);
            filterByCategory("city");
        });

        tabCommunity.setOnClickListener(v -> {
            updateTabs(tabCommunity);
            filterByCategory("community");
        });

        return view;
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

    private void filterByCategory(String category) {
        List<EventModel> filteredList = new ArrayList<>();
        for (EventModel item : fullList) {
            if (item.getCategory().equals(category)) {
                filteredList.add(item);
            }
        }
        eventAdapter.setFilteredList(filteredList);
    }

    private void updateTabs(TextView selectedTab) {
        tabAll.setTextColor(Color.GRAY);
        tabCity.setTextColor(Color.GRAY);
        tabCommunity.setTextColor(Color.GRAY);

        selectedTab.setTextColor(Color.BLACK);
    }
}