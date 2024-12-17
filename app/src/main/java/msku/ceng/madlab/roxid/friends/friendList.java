package msku.ceng.madlab.roxid.friends;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import msku.ceng.madlab.roxid.R;

public class friendList extends AppCompatActivity {
    Button addFriend;
    Button friendRequests;
    Button back;
    EditText friendUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_list);

        addFriend = findViewById(R.id.addFriend);
        friendRequests = findViewById(R.id.friendRequests);
        back = findViewById(R.id.backButton);
        friendUsername = findViewById(R.id.searchFriendById);


        addFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);
            }
        });
        friendUsername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);
            }
        });
        friendRequests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);
            }
        });
        
    }
}