package msku.ceng.madlab.roxid.mail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import msku.ceng.madlab.roxid.clubs.ClubsMain;
import msku.ceng.madlab.roxid.MainActivity;
import msku.ceng.madlab.roxid.R;

public class ConfirmationCode extends AppCompatActivity {
    EditText codeText;
    Button confirmButton;
    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation_code);
        codeText = findViewById(R.id.ConfirmationCode);
        confirmButton = findViewById(R.id.confirmButton);
        backButton = findViewById(R.id.backToMain);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert bundle != null;
                if (codeText.getText().toString().equalsIgnoreCase(bundle.getString("Verification Code"))){
                    Toast.makeText(view.getContext(),"you know the code",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(ConfirmationCode.this, ClubsMain.class);
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(view.getContext(),"wrong code",Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ConfirmationCode.this, MainActivity.class);
                startActivity(backIntent);
            }
        });

    }
}