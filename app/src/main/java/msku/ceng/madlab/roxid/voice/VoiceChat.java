package msku.ceng.madlab.roxid.voice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;

import msku.ceng.madlab.roxid.R;


public class VoiceChat extends AppCompatActivity {

    // Fill in your App ID, which can be generated in the Agora console
    private String myAppId = "29f451bf225e413380ebfb0e767a2fad";
    // Fill in the channel name
    private String channelName = "channel";
    // Fill in the temporary Token generated in the Agora console
    private String token = "007eJxSYGAyO5K76e13P8bjUZzm86ZN8M/yVgyrVpz21O6l4eTwbZIKDEaWaSamhklpRkamqSaGxsYWBqlJaUkGqeZm5olGaYkpO/ly0xsCGRl6pRtYGRkYGVgYGBlAfCYwyQwmWcAkO0NyRmJeXmoOAwMgAAD//0ITIRM=";

    private RtcEngine mRtcEngine;

    private static final int PERMISSION_REQ_ID = 22;

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

    private boolean checkPermissions() {
        for (String permission : getRequiredPermissions()) {
            int permissionCheck = ContextCompat.checkSelfPermission(VoiceChat.this, permission);
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
            runOnUiThread(() -> {
                Toast.makeText(VoiceChat.this, "Join channel success", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            runOnUiThread(() -> {
                Toast.makeText(VoiceChat.this, "User joined: " + uid, Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            super.onUserOffline(uid, reason);
            runOnUiThread(() -> {
                Toast.makeText(VoiceChat.this, "User offline: " + uid, Toast.LENGTH_SHORT).show();
            });
        }
    };

        private void initializeAndJoinChannel() {
            try {
                // Create an RtcEngineConfig object and configure it
                RtcEngineConfig config = new RtcEngineConfig();
                config.mContext = getBaseContext();
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



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_voice_chat);
            // If already authorized, initialize the RtcEngine and join the channel
            if (checkPermissions()) {
                initializeAndJoinChannel();
            } else {
                ActivityCompat.requestPermissions(this, getRequiredPermissions(), PERMISSION_REQ_ID);
            }
        }

        // System permission request callback
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (checkPermissions()) {
                initializeAndJoinChannel();
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (mRtcEngine != null) {
                // Leave the channel
                mRtcEngine.leaveChannel();
                mRtcEngine = null;
                // Destroy the engine
                RtcEngine.destroy();
            }
        }
    }