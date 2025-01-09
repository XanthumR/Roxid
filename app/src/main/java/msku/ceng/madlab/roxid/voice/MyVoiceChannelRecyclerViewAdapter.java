package msku.ceng.madlab.roxid.voice;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import msku.ceng.madlab.roxid.R;
import msku.ceng.madlab.roxid.database.Users;
import msku.ceng.madlab.roxid.voiceChannelUserHold.MyVoiceChannelUserHolderRecyclerViewAdapter;


public class MyVoiceChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyVoiceChannelRecyclerViewAdapter.ViewHolder> {

    private final List<VoiceChannel> mValues;
    private Context contextThis;



    public MyVoiceChannelRecyclerViewAdapter(List<VoiceChannel> items,Context context) {
        mValues = items;
        contextThis=context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_chat_fragment_item,parent,false);

        return new MyVoiceChannelRecyclerViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mVoiceChannelName.setText(mValues.get(position).getVoiceChannelName());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(contextThis,LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(new MyVoiceChannelUserHolderRecyclerViewAdapter(mValues.get(position).getJoinedUsers()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mVoiceChannelName;
        public RecyclerView recyclerView;
        public ViewHolder(View view) {
            super(view);
            mVoiceChannelName = view.findViewById(R.id.voice_channel_name);
            recyclerView = view.findViewById(R.id.userList);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mVoiceChannelName.getText() + "'";
        }
    }
}