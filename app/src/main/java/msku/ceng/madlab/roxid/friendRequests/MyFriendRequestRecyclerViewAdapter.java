package msku.ceng.madlab.roxid.friendRequests;

import static msku.ceng.madlab.roxid.Constants.usernameField;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.SessionManager;
import msku.ceng.madlab.roxid.friends.*;


import java.util.List;


public class MyFriendRequestRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendRequestRecyclerViewAdapter.ViewHolder> {

    private final List<Friend> mValues;



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    SessionManager sessionManager;



    public MyFriendRequestRecyclerViewAdapter(List<Friend> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_request,parent,false);

        return new ViewHolder(view).linkAdapter(this);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mFriendUsername.setText(mValues.get(position).getUsername());
        holder.mProfileImage.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ShapeableImageView mProfileImage;
        public final TextView mFriendUsername;
        public Friend mItem;
        public Button mAcceptButton;
        public Button mDeclineButton;
        public final View mView;
        private MyFriendRequestRecyclerViewAdapter adapter;

        public ViewHolder(View view) {
            super(view);
            mView =view;
            sessionManager = new SessionManager(view.getContext());
            mProfileImage = view.findViewById(R.id.friend_request_profile_image);
            mAcceptButton = view.findViewById(R.id.AcceptButton);
            mDeclineButton = view.findViewById(R.id.DeclineButton);
            mFriendUsername = view.findViewById(R.id.friend_request_username);

            mAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("users").document(sessionManager.getKeyUserId())
                            .collection("Friends").add(new Friend("profileImage",mFriendUsername.getText().toString()))
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(view.getContext(),"Friend added",Toast.LENGTH_SHORT).show();

                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(view.getContext(),"Error adding friend",Toast.LENGTH_SHORT).show();
                            });

                    db.collection("users").document(sessionManager.getKeyUserId())
                            .collection("Friend Requests")
                            .whereEqualTo(usernameField, mFriendUsername.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            // Delete the document
                                            adapter.mValues.remove(getBindingAdapterPosition());
                                            document.getReference().delete();
                                            adapter.notifyDataSetChanged();

                                        }
                                    } else {
                                        Log.w("Firestore", "Error getting documents.", task.getException());
                                    }
                                }
                            });

                }
            });

            mDeclineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });


        }
        public ViewHolder linkAdapter(MyFriendRequestRecyclerViewAdapter adapter){
            this.adapter=adapter;
            return this;
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mFriendUsername.getText() + "'";
        }
    }
}