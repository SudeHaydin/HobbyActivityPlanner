package msku.ceng3545.hobbyplanner.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import msku.ceng3545.hobbyplanner.R;

public class AddEventActivity extends AppCompatActivity {

    EditText etEventName, etEventDesc, etEventDate;
    Button btnSave;

    // Firebase Veritabanı
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // 1. Arayüz elemanlarını bağla
        etEventName = findViewById(R.id.etEventName);
        etEventDesc = findViewById(R.id.etEventDesc);
        etEventDate = findViewById(R.id.etEventDate);

        btnSave = findViewById(R.id.btnSave);

        // 2. Firebase'i başlat
        db = FirebaseFirestore.getInstance();

        // 3. Butona tıklayınca ne olsun?
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
            }
        });
    }

    private void saveToFirebase() {
        String title = etEventName.getText().toString().trim();
        String desc = etEventDesc.getText().toString().trim();
        String date = etEventDate.getText().toString().trim();

        // Boş bırakılmasın kontrolü
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- VERİ PAKETLEME ---
        Map<String, Object> event = new HashMap<>();

        event.put("title", title); // Başlık
        event.put("details", "📅 " + date + " | " + desc); // Detay
        event.put("category", "community"); // Kategori

        // --- SAYISAL DEĞERLER ---
        event.put("current", 0);  // Başlangıç katılımcı sayısı
        event.put("max", 50);     // Kontenjan

        // --- FIREBASE'E GÖNDERME ---
        db.collection("events")
                .add(event)
                .addOnSuccessListener(documentReference -> {

                    // --- BAŞARILI OLURSA BURASI ÇALIŞIR ---

                    // 1. Yeni oluşan ID'yi al
                    String newEventId = documentReference.getId();

                    // 2. Telefona "Bunu ben oluşturdum" diye kaydet (CreatedEvents)
                    android.content.SharedPreferences sharedPref = getSharedPreferences("CreatedEvents", MODE_PRIVATE);
                    android.content.SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(newEventId, true);
                    editor.apply();

                    // 3. Bilgi ver ve çık
                    Toast.makeText(AddEventActivity.this, "Etkinlik Oluşturuldu! 🎉", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    // --- HATA OLURSA BURASI ÇALIŞIR ---
                    Toast.makeText(AddEventActivity.this, "Hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}