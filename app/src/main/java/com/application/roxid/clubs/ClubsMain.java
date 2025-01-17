package com.application.roxid.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.roxid.friends.FriendList;
import com.application.roxid.login.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import com.application.roxid.R;

import com.application.roxid.SessionManager;
import com.application.roxid.TextShader;
import com.application.roxid.UserSettings;

public class ClubsMain extends AppCompatActivity {

    Button btnLogout;
    Button newClub;
    Button settings;
    Button friends;
    TextView clubs;
    private RecyclerView rowId;
    private ArrayList<String> listName;
    private ClubsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clubs_main);

        rowId = findViewById(R.id.rowId);
        rowId.setHasFixedSize(true);
        rowId.setLayoutManager(new GridLayoutManager(this,3));

        clubs = findViewById(R.id.textClubs);
        TextShader textShader = new TextShader();
        textShader.shaderStart(clubs,"#f105dd","#fb764f");


        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(ClubsMain.this);
                sessionManager.logout();

                Intent intent = new Intent(ClubsMain.this, MainActivity.class);
                // Bu kullanıcının çıkış yapmadan önce bütün aktivitilerini sıfırlamak anlamında kullanılır
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //TODO: Session kapatma yapılacak
            }
        });
        //1365

        newClub = findViewById(R.id.btnNewClub);
        newClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ClubsMain.this,Newclub.class);
                //startActivity(intent);
            }
        });

        friends = findViewById(R.id.btnFriends);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubsMain.this, FriendList.class);
                startActivity(intent);
            }
        });

        settings = findViewById(R.id.btnSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubsMain.this, UserSettings.class);
                startActivity(intent);
            }
        });




        //TODO: NOT TESTED
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SessionManager sessionManager = new SessionManager(ClubsMain.this);

        db.collection("users")
                .document(sessionManager.getKeyUserId())
                .collection("Clubs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            listName = new ArrayList<>();
                            for (QueryDocumentSnapshot club:task.getResult()){
                                String clubName = club.getString("Club Name");
                                if (clubName != null){
                                    listName.add(clubName);
                                }
                            }

                            adapter = new ClubsAdapter(ClubsMain.this,listName);

                            rowId.setAdapter(adapter);
                        }
                    }
                });


        /*

        SESSION INFOS CAN BE REACHABLE FROM EVERYWHERE

        // Başka bir sınıfta (örneğin, ClubsMain.java)
        SessionManager sessionManager = new SessionManager(this);

        // Kullanıcı bilgilerini al
        String username = sessionManager.getUsername();

        // Bilgileri kullan
        if (username != null) {
            Toast.makeText(this, "Welcome, " + username, Toast.LENGTH_SHORT).show();
            System.out.println("Welcome, " + username);
        } else {
            Toast.makeText(this, "No user is logged in.", Toast.LENGTH_SHORT).show();
            System.out.println("No user is logged in.");
        }
        */
    }
}