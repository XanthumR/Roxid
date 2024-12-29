package msku.ceng.madlab.roxid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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




    //! .set() Overwrite diğer verilere yapar ona SetOptions.merge() yapmak gerekir
    //! ama .update() overwrite yapmaz

    public void changeUsername(String currentUserID, String newUsername) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "username", newUsername
                );
    }

    public void changeFirstName(String currentUserID, String newFName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "firstName", newFName
                );
    }

    public void changeLastName(String currentUserID, String newLName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "lastName", newLName
                );
    }

    // TODO: Edit profile picture
    // TODO: Firebase Storage ücretli diyor.
}
