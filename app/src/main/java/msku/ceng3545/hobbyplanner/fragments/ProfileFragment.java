package msku.ceng3545.hobbyplanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import msku.ceng3545.hobbyplanner.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView btnHistory = view.findViewById(R.id.btnHistory);
        ImageView btnFavorites = view.findViewById(R.id.btnFavorites);
        ImageView btnUploads = view.findViewById(R.id.btnUploads);

        btnHistory.setOnClickListener(v ->
                Toast.makeText(getContext(), "Geçmiş Etkinlikleriniz burada listelenecek.", Toast.LENGTH_SHORT).show()
        );

        btnFavorites.setOnClickListener(v ->
                Toast.makeText(getContext(), "Favori Etkinlikleriniz burada görünecek.", Toast.LENGTH_SHORT).show()
        );

        btnUploads.setOnClickListener(v ->
                Toast.makeText(getContext(), "Kendi oluşturduğunuz etkinlikler burada olacak.", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}