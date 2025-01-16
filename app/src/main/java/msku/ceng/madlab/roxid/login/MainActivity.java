package msku.ceng.madlab.roxid.login;

import static msku.ceng.madlab.roxid.login.Hashing.hashPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import msku.ceng.madlab.roxid.InTheClubActivity;
import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.SessionManager;
import msku.ceng.madlab.roxid.TextShader;
import msku.ceng.madlab.roxid.clubs.ClubsMain;
import msku.ceng.madlab.roxid.friendRequests.FriendRequests;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    TextView logoText;
    TextView signUpText;
    Button loginButton;
    EditText email;
    EditText password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        logoText = findViewById(R.id.gradientText);
        signUpText = findViewById(R.id.signupTextView);
        loginButton = findViewById(R.id.loginButton);
        TextShader textShader = new TextShader();
        textShader.shaderStart(logoText,"#f105dd","#fb764f");
        textShader.shaderStart(signUpText,"#f206dc","#fc7b4e");

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = findViewById(R.id.emailEditText);
                password = findViewById(R.id.passwordEditText);
                System.out.println(password.getText().toString());

                checkIfValidUser(email.getText().toString(), password.getText().toString(), view);

            }
        });



    }

    private void checkIfValidUser(String email, String password, View view) {
        String newPassword = hashPassword(password);

        if (newPassword == null) {
            Toast.makeText(MainActivity.this, "Error hashing password", Toast.LENGTH_LONG).show();
            return;
        }

        db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", newPassword)
                .get(Source.SERVER) // Yalnızca sunucudan veri al
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            String username = task.getResult().getDocuments().get(0).getString("username");
                            String userId = task.getResult().getDocuments().get(0).getId();

                            SessionManager sessionManager = new SessionManager(MainActivity.this);
                            sessionManager.createSession(userId, username);

                            Intent intent = new Intent(MainActivity.this, ClubsMain.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "No user exists with this email", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String errorMessage = "Failed to connect to the server. Please check your internet connection.";
                        if (task.getException() != null) {
                            errorMessage += " Error: " + task.getException().getMessage();
                        }
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }



}