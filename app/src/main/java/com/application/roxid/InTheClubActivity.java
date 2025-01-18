package com.application.roxid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.application.roxid.clubs.ClubsMain;
import com.application.roxid.voice.VoiceChannelFragment;

public class InTheClubActivity extends AppCompatActivity {

    ImageButton addMsgChannel;
    ImageButton addVcChannel;
    Button backButton;
    Button leaveVoiceChannel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_the_club);



        backButton = findViewById(R.id.backButton);

        leaveVoiceChannel = findViewById(R.id.leaveVoiceChannel);

        addMsgChannel = findViewById(R.id.messageChannelAddButton);

        if (savedInstanceState == null) {
            VoiceChannelFragment myFragment = new VoiceChannelFragment();

            // Add the fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, myFragment) // Add the fragment to the container
                    .commit();
            leaveVoiceChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myFragment.leaveChannel();
                }
            });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InTheClubActivity.this, ClubsMain.class);
                startActivity(intent);
            }
        });

        addMsgChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InTheClubActivity.this, NewChannel.class);
                intent.putExtra("channelType", "text");
                startActivity(intent);

                /*
                SessionManager sessionManager = new SessionManager(InTheClubActivity.this);
                String clubID = sessionManager.getKeyClubId();
                //TODO: DİĞER AKTİVİTEDEN GELECEK OLAN EDİTTEXT BİLGİSİNE GÖRE GİRİLECEK
                addTextChannel(clubID, ChannelName);
                 */
            }
        });

        //TODO:BUNUN XML KISMINA MESSAGE CHANNEL İÇİN BİR FRAGMENT DAHA AÇILACAK.

        addVcChannel = findViewById(R.id.voiceChannelAddButton);
        addVcChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InTheClubActivity.this, NewChannel.class);
                intent.putExtra("channelType", "voice");
                startActivity(intent);

                //TODO: ÇIKARKEN CLUB ID SESSION BİLGİLERİ TEMİZLENECEK
            }
        });








    }
}