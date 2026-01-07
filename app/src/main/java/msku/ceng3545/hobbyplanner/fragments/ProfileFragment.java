//SUDE HAYDIN

package msku.ceng3545.hobbyplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import msku.ceng3545.hobbyplanner.R;
import msku.ceng3545.hobbyplanner.activities.LoginActivity;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail;
    private ImageView btnHistory, btnFavorites, btnUploads;
    private Button btnLogout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public ProfileFragment() {
        // Boş constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        tvName = view.findViewById(R.id.tvProfileName);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnFavorites = view.findViewById(R.id.btnFavorites);
        btnUploads = view.findViewById(R.id.btnUploads);
        btnLogout = view.findViewById(R.id.btnLogout);

        //veri  tabaından verileri çektik
        loadUserProfile();


        btnHistory.setOnClickListener(v ->
                Toast.makeText(getContext(), "Geçmiş Etkinlikleriniz burada listelenecek.", Toast.LENGTH_SHORT).show()
        );

        btnFavorites.setOnClickListener(v ->
                Toast.makeText(getContext(), "Favori Etkinlikleriniz burada görünecek.", Toast.LENGTH_SHORT).show()
        );

        btnUploads.setOnClickListener(v ->
                Toast.makeText(getContext(), "Kendi oluşturduğunuz etkinlikler burada olacak.", Toast.LENGTH_SHORT).show()
        );


        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            if (getActivity() != null) getActivity().finish();
        });

        return view;
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            tvEmail.setText(currentUser.getEmail());


            String userId = currentUser.getUid();
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String name = documentSnapshot.getString("name");
                                tvName.setText(name);
                            }
                        }
                    });
        }
    }
}