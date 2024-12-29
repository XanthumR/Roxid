package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import msku.ceng.madlab.roxid.clubs.ClubsMain;
import msku.ceng.madlab.roxid.voice.VoiceChat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import msku.ceng.madlab.roxid.clubs.ClubsMain;
import msku.ceng.madlab.roxid.friends.FriendList;
import msku.ceng.madlab.roxid.mail.ConfirmationCode;


public class MainActivity extends AppCompatActivity {
    TextView logoText;
    TextView signUpText;
    Button loginButton;
    Button buttonFriend;
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
        buttonFriend=findViewById(R.id.friendButton);
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

                ifValidUser(email.getText().toString(), password.getText().toString(), view);

            }
        });
        buttonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InTheClubActivity.class);
                startActivity(intent);
            }
        });



    }

    private void ifValidUser(String email, String password, View view){
        db.collection("users")
                .whereEqualTo("email",email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (!task.getResult().isEmpty()){
                            String username = task.getResult().getDocuments().get(0).getString("username");
                            String userId = task.getResult().getDocuments().get(0).getId();

                            SessionManager sessionManager = new SessionManager(MainActivity.this);
                            sessionManager.createSession(userId,username);

                            //System.out.println("Bunlar ID ve Username " + userId + " " + username);


                            Intent intent = new Intent(MainActivity.this, ClubsMain.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(view.getContext(),"No user exist with this mail", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(view.getContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}