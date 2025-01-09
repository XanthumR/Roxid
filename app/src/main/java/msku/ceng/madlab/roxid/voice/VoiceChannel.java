package msku.ceng.madlab.roxid.voice;

import java.util.List;

import msku.ceng.madlab.roxid.database.Users;

public class VoiceChannel {

    String VoiceChannelName;
    List<Users> joinedUsers;

    public VoiceChannel(String voiceChannelName, List<Users> joinedUsers) {
        VoiceChannelName = voiceChannelName;
        this.joinedUsers=joinedUsers;
    }

    public List<Users> getJoinedUsers(){
        return  joinedUsers;
    }


    public String getVoiceChannelName() {
        return VoiceChannelName;
    }
}
