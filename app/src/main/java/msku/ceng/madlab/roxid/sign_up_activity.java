package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class sign_up_activity extends AppCompatActivity {
    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    Button signUpButton;
    ImageButton backButton;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        signUpButton = findViewById(R.id.signUpButton);
        backButton = findViewById(R.id.backButton);
        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int code = 100000 + random.nextInt(900000);
                MailSender mailSender = new MailSender();
                mailSender.sendMail(Appdata.Reciever_Email,"Roxid Verification",
                        "Here is your confirmation code "+code);
                Intent intent = new Intent(sign_up_activity.this, confirmation_code.class);
                intent.putExtra("Verification Code", code);
                startActivity(intent);

            }
        });

    }

}