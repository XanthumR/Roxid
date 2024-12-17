package msku.ceng.madlab.roxid;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


public class MyVoiceChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyVoiceChannelRecyclerViewAdapter.ViewHolder> {

    private final List<VoiceChannel> mValues;

    public MyVoiceChannelRecyclerViewAdapter(List<VoiceChannel> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_chat_fragment_item,parent,false);

        return new MyVoiceChannelRecyclerViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mVoiceChannelName.setText(mValues.get(position).getVoiceChannelName());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mVoiceChannelName;

        public ViewHolder(View view) {
            super(view);
            mVoiceChannelName = view.findViewById(R.id.voice_channel_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mVoiceChannelName.getText() + "'";
        }
    }
}