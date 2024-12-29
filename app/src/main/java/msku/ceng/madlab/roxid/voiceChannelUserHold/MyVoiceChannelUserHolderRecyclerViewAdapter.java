package msku.ceng.madlab.roxid.voiceChannelUserHold;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.auth.User;

import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.database.Users;
import msku.ceng.madlab.roxid.databinding.FragmentVoiceChannelUserHolderItemBinding;

import java.util.List;


public class MyVoiceChannelUserHolderRecyclerViewAdapter extends RecyclerView.Adapter<MyVoiceChannelUserHolderRecyclerViewAdapter.ViewHolder> {

    private  List<Users> mValues;

    public MyVoiceChannelUserHolderRecyclerViewAdapter(List<Users> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_voice_channel_user_holder_item,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        holder.mContentView.setText(mValues.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateData(List<Users> newData) {
        this.mValues = newData; // Replace the old data
        notifyItemInserted(0);  // Refresh the RecyclerView
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ShapeableImageView profileImage;
        public final TextView mContentView;
        public final View mView;

        public Users userItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.username);
            profileImage = view.findViewById(R.id.user_profile_image);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}