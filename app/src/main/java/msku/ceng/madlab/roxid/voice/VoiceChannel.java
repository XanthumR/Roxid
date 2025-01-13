package msku.ceng.madlab.roxid.voice;

import java.util.List;

import msku.ceng.madlab.roxid.database.Users;

public class VoiceChannel {

    String VoiceChannelName;
    List<Users> joinedUsers;
    public int VoiceChannelIndex;
    public VoiceChannel(String voiceChannelName, List<Users> joinedUsers,int voiceChannelIndex) {
        VoiceChannelName = voiceChannelName;
        this.joinedUsers=joinedUsers;
        this.VoiceChannelIndex = voiceChannelIndex;
    }

    public List<Users> getJoinedUsers(){
        return  joinedUsers;
    }


    public String getVoiceChannelName() {
        return VoiceChannelName;
    }
}
