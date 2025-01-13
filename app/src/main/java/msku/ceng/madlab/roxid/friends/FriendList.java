package msku.ceng.madlab.roxid.friends;

import static msku.ceng.madlab.roxid.Constants.usernameField;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import msku.ceng.madlab.roxid.login.MainActivity;
import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.SessionManager;
import msku.ceng.madlab.roxid.friendRequests.FriendRequests;

public class FriendList extends AppCompatActivity {
    Button addFriend;
    Button friendRequests;
    Button back;
    EditText friendUsername;
    String requestId;

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
                SessionManager sessionManager = new SessionManager(view.getContext());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (!sessionManager.getUsername().equals(friendUsername.getText().toString())){
                    // Check if the user exist
                    db.collection("users")
                            .whereEqualTo(usernameField, friendUsername.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        requestId = task.getResult().getDocuments().get(0).getId();
                                        // Check if the user is already a friend
                                        db.collection("users")
                                                .document(sessionManager.getKeyUserId())
                                                .collection("Friends")
                                                .whereEqualTo(usernameField, friendUsername.getText().toString())
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                            Toast.makeText(view.getContext(),
                                                                    "You are already friends with " + task.getResult().getDocuments().get(0).get(usernameField).toString()
                                                                    , Toast.LENGTH_SHORT).show();
                                                        } else if (task.isSuccessful() && task.getResult().isEmpty()) {

                                                            db.collection("users").document(requestId).collection("Friend Requests").whereEqualTo(usernameField, sessionManager.getUsername())
                                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                                                Toast.makeText(view.getContext(), "You already sent friend request to " + friendUsername.getText().toString()
                                                                                        , Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                db.collection("users").document(requestId).collection("Friend Requests")
                                                                                        .add(new Friend("profileimage", sessionManager.getUsername()))
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                Toast.makeText(view.getContext(), "Friend request has been sent to " + friendUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });
                                                        }

                                                    }
                                                });
                                    } else {
                                        Toast.makeText(view.getContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


            }
                else{
                    Toast.makeText(view.getContext(), "You cant send yourself a friend request", Toast.LENGTH_SHORT).show();

                }
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
                Intent intent = new Intent(FriendList.this, MainActivity.class);
                startActivity(intent);
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                transition.startTransition(100);
                transition.reverseTransition(750);

            }
        });
        friendRequests.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendList.this, FriendRequests.class);
                TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                startActivity(intent);
                transition.startTransition(100);
                transition.reverseTransition(750);

            }
        });
        
    }
}