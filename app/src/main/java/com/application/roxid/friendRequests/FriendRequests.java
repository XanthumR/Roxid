package com.application.roxid.friendRequests;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.application.roxid.R;
import com.application.roxid.friends.FriendList;

public class FriendRequests extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_requests);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendRequests.this, FriendList.class);
                startActivity(intent);
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FriendRequests.this,FriendList.class);
        startActivity(intent);
    }
}