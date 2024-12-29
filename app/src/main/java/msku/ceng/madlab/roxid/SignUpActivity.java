package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import msku.ceng.madlab.roxid.mail.MailSender;
import msku.ceng.madlab.roxid.mail.ConfirmationCode;


public class SignUpActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    Button signUpButton;
    ImageButton backButton;


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
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (passwordEditText.getText().toString().equals(passwordAgainEditText.getText().toString())){
                    Random random = new Random();
                    int code = 100000 + random.nextInt(900000);
                    MailSender mailSender = new MailSender();
                    mailSender.sendMail(emailEditText.getText().toString(),"Roxid Verification",
                            "Here is your confirmation code "+code);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",usernameEditText.getText().toString());
                    bundle.putString("email",emailEditText.getText().toString());
                    bundle.putString("password",passwordEditText.getText().toString());
                    bundle.putString("Verification Code", String.valueOf(code));
                    Intent intent = new Intent(SignUpActivity.this, ConfirmationCode.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(view.getContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}