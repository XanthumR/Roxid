package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import msku.ceng.madlab.roxid.clubs.ClubsMain;
import msku.ceng.madlab.roxid.database.UserFunctions;

public class UserSettings extends AppCompatActivity {


    Button updatePicture;
    Button save;
    Button back;

    TextView userId;

    EditText username;
    EditText firstName;
    EditText lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SessionManager sessionManager = new SessionManager(UserSettings.this);
        String currentUserId = sessionManager.getKeyUserId();

        userId = findViewById(R.id.txtUserID);
        userId.setText(currentUserId);

        //TODO: EDITTEXT BOŞ İKEN SAVE YAPARSAK DB'DEKİ DEĞERLERİ SIFIRLIYOR
        save = findViewById(R.id.btnSaveSettings);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText'lerden yeni değerleri al
                username = findViewById(R.id.editTextUsername);
                firstName = findViewById(R.id.editTextFirstName);
                lastName = findViewById(R.id.editTextLastName);

                String newUsername = username.getText().toString();
                String newFirstName = firstName.getText().toString();
                String newLastName = lastName.getText().toString();
                String currentUsername = sessionManager.getUsername();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                /* BURADAKİ 2 KOD ARASINDA FONKSİYONEL HİÇBİR FARK YOK
                db.collection("users")
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                db.collection("users")
                                .get().addOnSuccessListener(queryDocumentSnapshots -> {

                        })
                                .addOnFailureListener(e -> {

                                });
                 */

                // Kullanıcı bilgilerini güncelle
                db.collection("users")
                        .whereEqualTo("username", currentUsername)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Kullanıcıyı bulduk, bilgileri güncelle
                                db.collection("users")
                                        .document(currentUserId)
                                        .update(
                                                "username", newUsername,
                                                "firstName", newFirstName,
                                                "lastName", newLastName
                                        )
                                        .addOnSuccessListener(aVoid -> {
                                            sessionManager.updateSessionUsername(newUsername);
                                            Toast.makeText(UserSettings.this, "Updates successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserSettings.this, ClubsMain.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(UserSettings.this, "Updates unsuccessful!", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(UserSettings.this, "There is no user!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(UserSettings.this, "Database error!", Toast.LENGTH_SHORT).show();
                        });
            }
        });



        back = findViewById(R.id.btnDontSave);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSettings.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}