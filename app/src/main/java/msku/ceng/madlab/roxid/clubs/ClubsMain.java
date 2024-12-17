package msku.ceng.madlab.roxid.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import msku.ceng.madlab.roxid.MainActivity;
import msku.ceng.madlab.roxid.R;

public class ClubsMain extends AppCompatActivity {

    Button btnBack;
    Button newClub;
    Button settings;
    Button friends;
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

        btnBack = findViewById(R.id.btnLogOut);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubsMain.this, MainActivity.class);
                startActivity(intent);
                //TODO: Session kapatma yapılacak
            }
        });
        //1365

        btnBack = findViewById(R.id.btnNewClub);
        btnBack.setOnClickListener(new View.OnClickListener() {
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
                //Intent intent = new Intent(ClubsMain.this,Friends.class);
                //startActivity(intent);
            }
        });

        settings = findViewById(R.id.btnSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ClubsMain.this,Settings.class);
                //startActivity(intent);
            }
        });


        listName = new ArrayList<>();

        listName.add(" Ozan");
        listName.add(" Şevval");
        listName.add(" MSKÜ");
        listName.add(" Rıdvan");
        listName.add(" Zeitnot");
        listName.add(" Allah");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");
        listName.add(" 1234567890abcçdefghıijklmnoöprsştuüvyz");

        System.out.println(listName);

        adapter = new ClubsAdapter(this,listName);

        rowId.setAdapter(adapter);


    }
}