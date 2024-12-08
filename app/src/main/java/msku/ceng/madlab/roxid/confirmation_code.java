package msku.ceng.madlab.roxid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class confirmation_code extends AppCompatActivity {
    EditText codeText;
    Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation_code);
        codeText = findViewById(R.id.ConfirmationCode);
        confirmButton = findViewById(R.id.confirmButton);
        Intent intent = getIntent();
        int code = intent.getIntExtra("Verification Code", 0);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeText.getText().toString().equalsIgnoreCase(String.valueOf(code))){
                    Toast.makeText(view.getContext(),"you know the code",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(),"wrong code",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}