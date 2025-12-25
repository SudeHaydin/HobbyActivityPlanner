package msku.ceng3545.hobbyplanner.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import msku.ceng3545.hobbyplanner.R;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_detail);


        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetails = findViewById(R.id.tvDetailDateTime);
        TextView tvParticipant = findViewById(R.id.tvParticipantCount);


        String title = getIntent().getStringExtra("title");
        String details = getIntent().getStringExtra("details");
        String status = getIntent().getStringExtra("status");


        tvTitle.setText(title);
        tvDetails.setText(details);
        tvParticipant.setText(status);
    }
}