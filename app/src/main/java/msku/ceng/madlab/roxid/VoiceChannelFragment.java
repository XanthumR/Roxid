package msku.ceng.madlab.roxid;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class VoiceChannelFragment extends Fragment {

    List<VoiceChannel> VoiceChannels = new ArrayList<>();


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    public VoiceChannelFragment() {
    }

    public static VoiceChannelFragment newInstance(int columnCount) {
        VoiceChannelFragment fragment = new VoiceChannelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        VoiceChannels.add(new VoiceChannel("voiceChannel1"));
        VoiceChannels.add(new VoiceChannel("voiceChannel2"));
        VoiceChannels.add(new VoiceChannel("voiceChannel3"));
        VoiceChannels.add(new VoiceChannel("voiceChannel4"));
        VoiceChannels.add(new VoiceChannel("voiceChannel5"));
        VoiceChannels.add(new VoiceChannel("voiceChannel6"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voice_chat_fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyVoiceChannelRecyclerViewAdapter(VoiceChannels));
        }
        return view;
    }
}