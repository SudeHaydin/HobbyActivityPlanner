package msku.ceng3545.hobbyplanner.activities;
//MEHMET EKREM ERKAN
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import msku.ceng3545.hobbyplanner.R;

public class AddEventActivity extends AppCompatActivity {

    EditText etEventName, etEventDesc, etEventDate;
    Spinner spCategory; // Yeni eklediğimiz Spinner
    Button btnSave;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // 1. Arayüz elemanlarını bağla
        etEventName = findViewById(R.id.etEventName);
        etEventDesc = findViewById(R.id.etEventDesc);
        etEventDate = findViewById(R.id.etEventDate);
        spCategory = findViewById(R.id.spCategory); // Spinner'ı bağladık
        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        // --- SPINNER'I DOLDURMA ---
        // Kategorilerimiz (Keşfet sayfasındaki butonlarla AYNI olmalı)
        String[] categories = {"Genel", "Spor", "Sanat", "Teknoloji", "Müzik"};

        // Adaptör ile listeyi Spinner'a bağla
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);
        // -------------------------

        btnSave.setOnClickListener(v -> saveToFirebase());
    }

    private void saveToFirebase() {
        String title = etEventName.getText().toString().trim();
        String desc = etEventDesc.getText().toString().trim();
        String date = etEventDate.getText().toString().trim();

        // Spinner'dan seçilen kategoriyi al
        String selectedCategory = spCategory.getSelectedItem().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Veri Paketleme
        Map<String, Object> event = new HashMap<>();
        event.put("title", title);
        event.put("details", "📅 " + date + " | " + desc);

        // ARTIK "community" YERİNE SEÇİLEN KATEGORİYİ KAYDEDİYORUZ
        event.put("category", selectedCategory);

        event.put("current", 0);
        event.put("max", 50);

        db.collection("events")
                .add(event)
                .addOnSuccessListener(documentReference -> {
                    String newEventId = documentReference.getId();

                    // Telefona "Bunu ben oluşturdum" diye kaydet
                    getSharedPreferences("CreatedEvents", MODE_PRIVATE)
                            .edit()
                            .putBoolean(newEventId, true)
                            .apply();

                    Toast.makeText(AddEventActivity.this, "Etkinlik Oluşturuldu! 🎉", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddEventActivity.this, "Hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}