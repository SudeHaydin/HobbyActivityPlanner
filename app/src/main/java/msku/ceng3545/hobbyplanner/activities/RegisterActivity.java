//SUDE HAYDIN
package msku.ceng3545.hobbyplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// R dosyasını bulamazsa kendi paket ismine göre (import msku...R;) ekle
import msku.ceng3545.hobbyplanner.MainActivity;
import msku.ceng3545.hobbyplanner.R;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView loginRedirectText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge ve ViewCompat satırlarını kaldırdık, çünkü hataya sebep oluyordu.
        setContentView(R.layout.activity_register);

        // Firebase Başlat
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Elemanları Tanımla
        nameEditText = findViewById(R.id.registerNameEditText);
        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Kayıt Ol Butonu
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameEditText.setError("İsim gerekli");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("E-posta gerekli");
                    return;
                }
                if (password.length() < 6) {
                    passwordEditText.setError("Şifre en az 6 karakter olmalı");
                    return;
                }

                registerUser(name, email, password);
            }
        });

        // Giriş Yap'a Dön
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userId = mAuth.getCurrentUser().getUid();
                        saveUserToFirestore(userId, name, email);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Kayıt Hatası: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(String userId, String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("userId", userId);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Hesap Oluşturuldu!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Veritabanı Hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}