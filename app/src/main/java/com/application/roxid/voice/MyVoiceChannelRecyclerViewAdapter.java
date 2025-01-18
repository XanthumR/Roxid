package com.application.roxid.voice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;


import com.application.roxid.database.Users;
import com.application.roxid.voiceChannelUserHold.MyVoiceChannelUserHolderRecyclerViewAdapter;
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

import com.application.roxid.Constants;
import com.application.roxid.R;
import com.application.roxid.SessionManager;


public class MyVoiceChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyVoiceChannelRecyclerViewAdapter.ViewHolder> {

    private final List<VoiceChannel> mValues;
    private Context contextThis;
    private int prev;
    private Constants constants = Constants.getInstance();
    private MyVoiceChannelUserHolderRecyclerViewAdapter voiceHolderAdapter ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Users user;
    SessionManager sessionManager ;
    private  String prevChannel = null;
    private Fragment fragment;
    private VoiceChat voiceChat;


    public MyVoiceChannelRecyclerViewAdapter(List<VoiceChannel> items, Context context, Fragment fragment) {
        mValues = items;
        contextThis=context;
        this.fragment=fragment;
        // attaches a event listener to every voice channel
        db.collection("Clubs").document(constants.getClubName()).collection("voice channels")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot voiceChannels: task.getResult()){
                            List<Users> joinedUsers = new ArrayList<>();
                            db.collection("Clubs").document(constants.getClubName()).collection("voice channels")
                                    .document(voiceChannels.getId()).collection("subscribed")
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
                                                        if (dc.getDocument().getString("username")!=null){
                                                            System.out.println("added");
                                                            if (dc.getDocument().getString("username").equals(sessionManager.getUsername())){
                                                                prevChannel=voiceChannels.getString("voice channel name");
                                                            }
                                                            joinedUsers.add(new Users(dc.getDocument().getString("username"),"default picture"));
                                                        }
                                                        break;
                                                    case MODIFIED:
                                                        break;
                                                    case REMOVED:
                                                        Users remUser=null;
                                                        for(Users user: joinedUsers){
                                                            if (user.getUsername().equals(dc.getDocument().getString("username"))){
                                                                remUser = user;

                                                            }
                                                        }
                                                        if (remUser!=null){
                                                            joinedUsers.remove(remUser);
                                                        }
                                                        break;
                                                }
                                            }
                                            for (int i = 0; i < mValues.size(); i++) {
                                                if (mValues.get(i).VoiceChannelName.equals(voiceChannels.getString("voice channel name"))){
                                                    mValues.get(i).joinedUsers = joinedUsers;
                                                }
                                            }

                                            notifyDataSetChanged();
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

    protected void leaveVchat(){
        if (voiceChat!=null){
            voiceChat.leave();
        }
    }

    private void destroyRtcengine(){
        if (voiceChat!=null){
            voiceChat.destroyRtcEngine();
        }
    }

    public void destroyAndExit(){
        // triggered when the fragment is destroyed
            leaveVchat();
            destroyRtcengine();
        db.collection("Clubs").document(constants.getClubName())
                .collection("voice channels").whereEqualTo("voice channel name",prevChannel)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot prev:task.getResult()){
                            db.collection("Clubs").document(constants.getClubName())
                                    .collection("voice channels").document(prev.getId()).collection("subscribed")
                                    .whereEqualTo("username",sessionManager.getUsername())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    // Delete the document
                                                    document.getReference().delete();
                                                }
                                            } else {
                                                Log.w("Firestore", "Error getting documents.", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });

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
                // add the user to the voice channel's subscriber collection
                if (!(holder.mVoiceChannelName.getText().toString()).equals(prevChannel)){
                    SessionManager sessionManager = new SessionManager(view.getContext());
                    db.collection("Clubs").document(constants.getClubName()).collection("voice channels").whereEqualTo("voice channel name",holder.mVoiceChannelName.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        for (QueryDocumentSnapshot voiceChannel: task.getResult()){
                                            db.collection("Clubs").document(constants.getClubName()).collection("voice channels").document(voiceChannel.getId()).collection("subscribed")
                                                    .add(new Users(holder.sessionManager.getUsername(),"default pic"))
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                            leaveVchat();
                                                            destroyRtcengine();
                                                            voiceChat = new VoiceChat(contextThis,holder.mVoiceChannelName.getText().toString()
                                                                    ,sessionManager.getUsername(),fragment.getActivity());
                                                            voiceChat.join();
                                                        }
                                                    });

                                        }
                                    }
                                }
                            });


                    // remove user from the previous channel in database if there is a previous channel
                    if (prevChannel!=null){
                        db.collection("Clubs").document(constants.getClubName())
                                .collection("voice channels").whereEqualTo("voice channel name",prevChannel)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot prev:task.getResult()){
                                            db.collection("Clubs").document(constants.getClubName())
                                                    .collection("voice channels").document(prev.getId()).collection("subscribed")
                                                    .whereEqualTo("username",sessionManager.getUsername())
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                    // Delete the document
                                                                    document.getReference().delete();
                                                                }
                                                            } else {
                                                                Log.w("Firestore", "Error getting documents.", task.getException());
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                    }
                    prevChannel =holder.mVoiceChannelName.getText().toString();
                }



            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setContextThis(Context context){
        contextThis =context;
        sessionManager = new SessionManager(contextThis);
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