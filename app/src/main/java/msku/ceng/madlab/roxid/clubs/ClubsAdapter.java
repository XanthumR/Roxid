package msku.ceng.madlab.roxid.clubs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import msku.ceng.madlab.roxid.Constants;
import msku.ceng.madlab.roxid.R;

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


    @Override
    public void onBindViewHolder(@NonNull ClubsViewObjects holder, int position) {

        System.out.println("Clublar başarıyla oluşturuldu");

        String isimler = list.get(position);

        holder.clubButton.setText("Button" + isimler);

        holder.clubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants constants = Constants.getInstance();
                constants.setClubName(isimler);
                System.out.println("BUTONA TIKLANDI");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClubsViewObjects extends RecyclerView.ViewHolder{

        public CardView cardId;
        public Button clubButton;

        public ClubsViewObjects(@NonNull View view) {
            super(view);

            cardId = view.findViewById(R.id.cardId);
            clubButton = view.findViewById(R.id.denemeButton);
        }
    }
}
