
package com.application.roxid.database;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.application.roxid.SessionManager;

public class ChatFunctions {

    public static void addTextChannel(String clubID, String channelName) {
        // Instance data
        Map<String, Object> newChannel = new HashMap<>();
        newChannel.put("channelName", channelName);
        newChannel.put("createdAt", FieldValue.serverTimestamp());
        newChannel.put("members", new ArrayList<String>());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Clubs")
                .document(clubID)
                .collection("messageChannels")
                .add(newChannel)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firebase", "New Text Channel is Created: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firebase", "Error during adding new text channel", e);
                });
    }

    public static void addVoiceChannel(String clubID, String channelName){
        // Instance data
        Map<String, Object> newChannel = new HashMap<>();
        newChannel.put("voice channel name", channelName);
        newChannel.put("createdAt", FieldValue.serverTimestamp());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Clubs")
                .document(clubID)
                .collection("voice channels")
                .add(newChannel)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firebase", "New Voice Channel is Created: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firebase", "Error during adding new voice channel", e);
                });
    }

    public static void addMessageToChannel(String channelId, String message, String clubID, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SessionManager sessionManager = new SessionManager(context);
        String currentUserId = sessionManager.getKeyUserId();

        // Mesaj verisini hazırlayın
        Map<String, Object> newMessage = new HashMap<>();
        newMessage.put("senderId", currentUserId);
        newMessage.put("message", message);
        newMessage.put("timestamp", FieldValue.serverTimestamp());

        db.collection("Clubs")
                .document(clubID)
                .collection("messageChannels") // Tutarlı koleksiyon adı
                .document(channelId)
                .collection("Messages")
                .add(newMessage)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firebase", "Mesaj başarıyla eklendi: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firebase", "Mesaj eklenirken hata oluştu", e);
                });
    }

    //TODO: Eklenmiş olabilir
    public static void connectToVoice(){}





    //TODO: Delete a chat
    //TODO: Rename a chat
}
