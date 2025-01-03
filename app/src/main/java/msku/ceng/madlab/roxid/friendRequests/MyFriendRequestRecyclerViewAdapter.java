package msku.ceng.madlab.roxid.friendRequests;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.friends.*;


import java.util.List;


public class MyFriendRequestRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendRequestRecyclerViewAdapter.ViewHolder> {

    private final List<Friend> mValues;

    public MyFriendRequestRecyclerViewAdapter(List<Friend> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_request,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mFriendUsername.setText(mValues.get(position).getUsername());
        holder.mProfileImage.setImageResource(R.mipmap.ic_launcher);
        holder.mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(holder.mFriendUsername.getText().toString());
            }
        });

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

        public ViewHolder(View view) {
            super(view);
            mView =view;
            mProfileImage = view.findViewById(R.id.friend_request_profile_image);
            mAcceptButton = view.findViewById(R.id.AcceptButton);
            mDeclineButton = view.findViewById(R.id.DeclineButton);
            mFriendUsername = view.findViewById(R.id.friend_request_username);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFriendUsername.getText() + "'";
        }
    }
}