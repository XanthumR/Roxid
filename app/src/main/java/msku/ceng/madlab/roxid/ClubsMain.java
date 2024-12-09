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

    /*
    private RecyclerView recyclerView;
    private ClubsAdapter adapter;
    private List<String> dataList;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clubs_main);

        rowId = findViewById(R.id.recyclerView);
        rowId.setHasFixedSize(true);
        rowId.setLayoutManager(new GridLayoutManager(this,3));

        listName = new ArrayList<>();

        listName.add("Ozan");
        listName.add("Şevval");
        listName.add("MSKÜ");
        listName.add("Rıdvan");
        listName.add("Zeitnot");

        System.out.println(listName);

        adapter = new ClubsAdapter(this,listName);

        rowId.setAdapter(adapter);


        /*
        recyclerView = findViewById(R.id.recyclerView);

        //3 sütunlu düzen
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = fetchDataFromDatabase();

        System.out.println(dataList);

        // Adapteri ayarla
        adapter = new ClubsAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

         */
    }

    /*
    // Verileri taklit eden bir fonksiyon
    private List<String> fetchDataFromDatabase() {
        List<String> dummyData = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            dummyData.add("Button " + i);
        }
        return dummyData;
    }

     */
}