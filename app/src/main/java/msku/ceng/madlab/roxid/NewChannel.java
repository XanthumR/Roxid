package msku.ceng.madlab.roxid;

import static msku.ceng.madlab.roxid.database.ChatFunctions.addTextChannel;
import static msku.ceng.madlab.roxid.database.ChatFunctions.addVoiceChannel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewChannel extends AppCompatActivity {

    TextView label;
    TextView channelName;
    EditText editTextChannel;
    Button btnSave;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_channel);
        String channelType = getIntent().getStringExtra("channelType");


        channelName = findViewById(R.id.newChannelLabel);
        label = findViewById(R.id.channelLabel);
        btnSave = findViewById(R.id.saveChannel);
        btnBack = findViewById(R.id.backToClub);

        if("voice".equals(channelType)){
            label.setText("Create a New Voice Channel");


            editTextChannel = findViewById(R.id.editTextNewChannel);
            editTextChannel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //Empty
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    channelName.setText(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //Empty
                }
            });

            btnSave.setOnClickListener(view -> {
                String channelName = editTextChannel.getText().toString();
                SessionManager sessionManager = new SessionManager(NewChannel.this);
                String clubID = sessionManager.getKeyClubId();
                Constants constants = Constants.getInstance();
                String clubName =constants.getClubName();
                addVoiceChannel(clubName, channelName);
            });

        }
        else if ("text".equals(channelType)){

            label.setText("Create a New Text Channel");



            editTextChannel = findViewById(R.id.editTextNewChannel);
            editTextChannel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //Empty
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    channelName.setText(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //Empty
                }
            });

            btnSave.setOnClickListener(view -> {
                String channelName = editTextChannel.getText().toString();
                SessionManager sessionManager = new SessionManager(NewChannel.this);
                String clubID = sessionManager.getKeyClubId();
                addTextChannel(clubID, channelName);
            });

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewChannel.this, InTheClubActivity.class);
                startActivity(intent);
            }
        });
    }
}