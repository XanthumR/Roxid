package msku.ceng.madlab.roxid;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewObjects> {

    private Context context;
    private List<String> list;

    public ClubsAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    // Club Tasarımı burada olacak
    public ClubsViewObjects onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_clubs,parent,false);

        return new ClubsViewObjects(itemView);
    }


    //TODO: BURAYA GİRMİYOR DÜZELMESİ GEREKLİ DÜZGÜN BAĞLANMAMIŞ
    @Override
    public void onBindViewHolder(@NonNull ClubsViewObjects holder, int position) {

        System.out.println("DENEMEEEE");
        String isimler = list.get(position);

        holder.clubText.setText(isimler);
        holder.clubButton.setText("Button" + isimler);

        holder.cardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Seçilen isim: " + isimler, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClubsViewObjects extends RecyclerView.ViewHolder{

        public CardView cardId;
        public TextView clubText;
        public Button clubButton;

        public ClubsViewObjects(@NonNull View view) {
            super(view);

            cardId = view.findViewById(R.id.cardId);
            clubText = view.findViewById(R.id.denemeText);
            clubButton = view.findViewById(R.id.denemeButton);
        }
    }
}
