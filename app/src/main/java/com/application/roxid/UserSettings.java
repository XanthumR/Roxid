package com.application.roxid;

import static com.application.roxid.database.UserFunctions.changeFirstName;
import static com.application.roxid.database.UserFunctions.changeLastName;
import static com.application.roxid.database.UserFunctions.changeUsername;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.roxid.login.MainActivity;

public class UserSettings extends AppCompatActivity {

    Button updatePicture;
    ImageView userPicture;

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



        updatePicture = findViewById(R.id.btnEditUserPicture);
        userPicture = findViewById(R.id.imageUserPicture);


        /*
        registerResult();
        updatePicture.setOnClickListener(view -> pickImage());
         */

        // TODO: EDITTEXT BOŞ İKEN SAVE YAPARSAK DB'DEKİ DEĞERLERİ SIFIRLIYOR
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


                if (!newUsername.isEmpty()) {
                    changeUsername(currentUserId, newUsername);
                    sessionManager.updateSessionUsername(newUsername);
                }
                if (!newFirstName.isEmpty()) {
                    changeFirstName(currentUserId, newFirstName);
                }
                if (!newLastName.isEmpty()) {
                    changeLastName(currentUserId, newLastName);
                } else if (newUsername.isEmpty() && newFirstName.isEmpty() && newLastName.isEmpty()) {
                    Toast.makeText(UserSettings.this,"You must type a value to save", Toast.LENGTH_SHORT).show();
                }

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

                back = findViewById(R.id.btnDontSave);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UserSettings.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
