package msku.ceng3545.hobbyplanner.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import msku.ceng3545.hobbyplanner.R;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Senin tasarım dosyanın adı event_details ise burayı öyle bırak
        setContentView(R.layout.event_detail);

        // XML'deki ID'lerinle eşleştir
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetails = findViewById(R.id.tvDetailDateTime);
        TextView tvParticipant = findViewById(R.id.tvParticipantCount);

        // Önceki sayfadan (Adapter'dan) gelen verileri al
        String title = getIntent().getStringExtra("title");
        String details = getIntent().getStringExtra("details");
        String status = getIntent().getStringExtra("status");

        // Ekrana yazdır
        tvTitle.setText(title);
        tvDetails.setText(details);
        tvParticipant.setText(status);
    }
}