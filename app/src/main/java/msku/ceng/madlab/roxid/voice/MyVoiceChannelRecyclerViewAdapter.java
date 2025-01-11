package msku.ceng.madlab.roxid.voice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import msku.ceng.madlab.roxid.Constants;
import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.SessionManager;
import msku.ceng.madlab.roxid.database.Users;
import msku.ceng.madlab.roxid.voiceChannelUserHold.MyVoiceChannelUserHolderRecyclerViewAdapter;


public class MyVoiceChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyVoiceChannelRecyclerViewAdapter.ViewHolder> {

    private final List<VoiceChannel> mValues;
    List<Users> joinedUsers = new ArrayList<>();
    private Context contextThis;
    private int prev;

    private MyVoiceChannelUserHolderRecyclerViewAdapter voiceHolderAdapter ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Users user;



    public MyVoiceChannelRecyclerViewAdapter(List<VoiceChannel> items,Context context) {
        mValues = items;
        contextThis=context;
        db.collection("Clubs").document("School Project").collection("voice channels")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot voiceChannels: task.getResult()){
                            db.collection("Clubs").document("School Project").collection("voice channels")
                                    .document(voiceChannels.getString("voice channel name")).collection("subscribed")
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                System.err.println("Error listening to collection: " + error);
                                                return;
                                            }
                                            for (DocumentChange dc : value.getDocumentChanges()) {
                                                switch (dc.getType()) {
                                                    case ADDED:
                                                        System.out.println("Document added: " + dc.getDocument().getData());
                                                        break;
                                                    case MODIFIED:
                                                        System.out.println("Document modified: " + dc.getDocument().getData());
                                                        break;
                                                    case REMOVED:
                                                        System.out.println("Document removed: " + dc.getDocument().getData());
                                                        break;
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_chat_fragment_item,parent,false);

        return new MyVoiceChannelRecyclerViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mVoiceChannelName.setText(mValues.get(position).getVoiceChannelName());
        prev = holder.getBindingAdapterPosition();
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(contextThis,LinearLayoutManager.VERTICAL, false));
        voiceHolderAdapter = new MyVoiceChannelUserHolderRecyclerViewAdapter(mValues.get(position).getJoinedUsers());
        holder.recyclerView.setAdapter(voiceHolderAdapter);

        holder.joinVC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants constants = Constants.getInstance();
                db.collection("Clubs").document("School Project").collection("voice channels")
                        .document(holder.mVoiceChannelName.getText().toString()).collection("subscribed")
                        .add(new Users(holder.sessionManager.getUsername(),"default pic"))
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(view.getContext(), "joined channel", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mVoiceChannelName;
        public RecyclerView recyclerView;
        public LinearLayout joinVC;
        public SessionManager sessionManager;
        public ViewHolder(View view) {
            super(view);
            mVoiceChannelName = view.findViewById(R.id.voice_channel_name);
            recyclerView = view.findViewById(R.id.userList);
            joinVC = view.findViewById(R.id.channelJoinButton);
            sessionManager = new SessionManager(view.getContext());


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mVoiceChannelName.getText() + "'";
        }
    }
}