package msku.ceng.madlab.roxid.voice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
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


public class VoiceChannelFragment extends Fragment {

    List<VoiceChannel> VoiceChannels = new ArrayList<>();
    List<Users> joinedUsers = new ArrayList<>();

    private Constants constants = Constants.getInstance();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private MyVoiceChannelRecyclerViewAdapter myVoiceChannelRecyclerViewAdapter=new MyVoiceChannelRecyclerViewAdapter(VoiceChannels,getContext(),VoiceChannelFragment.this) ;
    int indx=0;
    public VoiceChannelFragment() {
    }

    public static VoiceChannelFragment newInstance(int columnCount) {
        VoiceChannelFragment fragment = new VoiceChannelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void leaveChannel(){
        myVoiceChannelRecyclerViewAdapter.destroyAndExit();
    }

    

    @Override
    public void onStop() {
        super.onStop();
        myVoiceChannelRecyclerViewAdapter.destroyAndExit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        myVoiceChannelRecyclerViewAdapter.setContextThis(getContext());

        // gets the voice channels from database and add them to the VoiceChannels List then notify the
        // recycling view adapter to show it on screen
        db.collection("Clubs").whereEqualTo("Club Name",constants.getClubName())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                                for (QueryDocumentSnapshot club: task.getResult()){
                                    db.collection("Clubs").document(club.getId()).collection("voice channels")
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.getResult().isEmpty()){
                                                        return;
                                                    }
                                                    indx=0;
                                                         for (QueryDocumentSnapshot voiceChannel: task.getResult()){
                                                             db.collection("Clubs").document(club.getId()).collection("voice channels")
                                                                     .document(voiceChannel.getId()).collection("subscribed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                         @Override
                                                                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                             joinedUsers = new ArrayList<>();
                                                                             for (QueryDocumentSnapshot user: task.getResult()){
                                                                                 joinedUsers.add(new Users(user.getString("username"),"default picture"));
                                                                             }
                                                                             VoiceChannels.add(indx,new VoiceChannel(voiceChannel.getString("voice channel name"),joinedUsers,indx));
                                                                             myVoiceChannelRecyclerViewAdapter.notifyDataSetChanged();
                                                                             indx++;
                                                                         }
                                                                     });
                                                         }

                                                }
                                            });
                                }
                        }
                        else{
                            Toast.makeText(getContext(),"Club doesn't exist",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voice_chat_fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(myVoiceChannelRecyclerViewAdapter);
        }
        return view;
    }

}