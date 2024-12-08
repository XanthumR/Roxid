package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView logoText;
    TextView signUpText;
    Button loginButton;
    Button buttonFriend;
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
                Intent intent = new Intent(MainActivity.this,sign_up_activity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, friendList.class);
                startActivity(intent);
            }
        });

    }

}