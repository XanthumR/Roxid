package msku.ceng.madlab.roxid.friends;

import static msku.ceng.madlab.roxid.Constants.usernameField;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import msku.ceng.madlab.roxid.MainActivity;
import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.SessionManager;
import msku.ceng.madlab.roxid.clubs.ClubsMain;


/**
 * A fragment representing a list of Items.
 */
public class FriendFragment extends Fragment {

    List<Friend> friends = new ArrayList<>();

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    SessionManager sessionManager;

    RecyclerView recyclerView;

    MyFriendRecyclerViewAdapter myFriendRecyclerViewAdapter = new MyFriendRecyclerViewAdapter(friends);


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendFragment() {
    }

    @SuppressWarnings("unused")
    public static FriendFragment newInstance(int columnCount) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this.getContext());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(sessionManager.getKeyUserId())
                .collection("Friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot friend: task.getResult()) {
                                friends.add(new Friend("picture", friend.getString(usernameField)));
                            }
                            myFriendRecyclerViewAdapter.notifyDataSetChanged();
                        }


                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(myFriendRecyclerViewAdapter);
        }
        return view;
    }
}