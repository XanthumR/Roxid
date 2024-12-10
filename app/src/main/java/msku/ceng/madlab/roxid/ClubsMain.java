package msku.ceng.madlab.roxid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClubsMain extends AppCompatActivity {

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

        listName = new ArrayList<>();

        listName.add("Ozan");
        listName.add("Şevval");
        listName.add("MSKÜ");
        listName.add("Rıdvan");
        listName.add("Zeitnot");
        listName.add("Allah");

        System.out.println(listName);

        adapter = new ClubsAdapter(this,listName);

        rowId.setAdapter(adapter);


    }
}