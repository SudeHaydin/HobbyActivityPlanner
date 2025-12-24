package msku.ceng3545.hobbyplanner.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import msku.ceng3545.hobbyplanner.R;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        EditText etName = findViewById(R.id.etEventName);
        EditText etDesc = findViewById(R.id.etEventDesc);
        EditText etDate = findViewById(R.id.etEventDate);
        View btnCreate = findViewById(R.id.btnCreateFinal);


        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Lütfen etkinlik adı girin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Etkinlik oluşturuldu! (Demo)", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}