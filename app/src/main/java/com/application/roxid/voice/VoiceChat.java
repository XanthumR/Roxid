package com.application.roxid.voice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import android.widget.Toast;

import androidx.core.content.ContextCompat;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import com.application.roxid.apiKeys;

import com.application.roxid.R;


public class VoiceChat {

    // Fill in your App ID, which can be generated in the Agora console
    private String myAppId = apiKeys.getAppId();
    // Fill in the channel name
    private String channelName;
    // Fill in the temporary Token generated in the Agora console
    private String token;

    private RtcEngine mRtcEngine;

    private static final int PERMISSION_REQ_ID = 22;


    private TokenBuilder tokenBuilder = new TokenBuilder();

    private Context context;

    private Activity activity;
    // Get the permissions required for experiencing real-time audio interaction
    private String[] getRequiredPermissions(){
        // Determine the permissions required when targetSDKVersion is 31 or above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO, // Record audio permission
                    Manifest.permission.READ_PHONE_STATE, // Read phone state permission
                    Manifest.permission.BLUETOOTH_CONNECT // Bluetooth connection permission
            };
        } else {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO,
            };
        }
    }

    public VoiceChat(Context context, String channelName,String username,Activity activity) {
        this.context = context;
        this.channelName = channelName;
        this.token = tokenBuilder.createToken(channelName,username);
        System.out.println(channelName+" "+this.token);
        this.activity=activity;
    }

    private boolean checkPermissions() {
        for (String permission : getRequiredPermissions()) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            activity.runOnUiThread(() -> {
                Toast.makeText(context, "Join channel success", Toast.LENGTH_SHORT).show();
            });
        }


        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            activity.runOnUiThread(() -> {
                Toast.makeText(context, "User joined: " + uid, Toast.LENGTH_SHORT).show();
            });
        }


        @Override
        public void onUserOffline(int uid, int reason) {
            super.onUserOffline(uid, reason);
            activity.runOnUiThread(() -> {
                Toast.makeText(context, "User offline: " + uid, Toast.LENGTH_SHORT).show();
            });
        }
    };



        private void initializeAndJoinChannel() {
            try {
                // Create an RtcEngineConfig object and configure it
                RtcEngineConfig config = new RtcEngineConfig();
                config.mContext = context;
                config.mAppId = myAppId;
                config.mEventHandler = mRtcEventHandler;
                // Create and initialize the RtcEngine
                mRtcEngine = RtcEngine.create(config);
            } catch (Exception e) {
                throw new RuntimeException("Check the error.");
            }

            // Create a ChannelMediaOptions object and configure it
            ChannelMediaOptions options = new ChannelMediaOptions();
            // Set the user role to BROADCASTER (host) or AUDIENCE (audience)
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
            // Set the channel profile
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
            // Publish the audio collected by the microphone
            options.publishMicrophoneTrack = true;
            // Automatically subscribe to all audio streams
            options.autoSubscribeAudio = true;
            // Join the channel using a uid, temporary token and channel name.
            // Ensure that the uid is unique within the channel.
            // If you set the uid to 0, the engine  generates a random uid.
            // The onJoinChannelSuccess callback is triggered upon success.
            mRtcEngine.joinChannel(token, channelName, 0, options);
        }

public void join(){

    if (checkPermissions()) {
        Toast.makeText(context, "trying to join", Toast.LENGTH_SHORT).show();
        initializeAndJoinChannel();
    }
}
public void leave(){
            if (mRtcEngine!=null){
                mRtcEngine.leaveChannel();
                Toast.makeText(context, "left channel", Toast.LENGTH_SHORT).show();
            }
}

public void destroyRtcEngine(){
            if (mRtcEngine!=null){
                mRtcEngine = null;
                // Destroy the engine
                RtcEngine.destroy();
            }
}


    }