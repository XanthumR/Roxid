package msku.ceng.madlab.roxid;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "userSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager (Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //TODO: User ID'leri Integer olursa buraların değişmesi gerekli
    // Kullanıcı bilgilerini session'a kaydeder
    public void createSession(String userId, String username) {
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    // Kullanıcı adını getirme
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getKeyUserId() {
        return sharedPreferences.getString(KEY_USER_ID,null);
    }

    public void updateSessionUsername(String username){
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Oturum kontrolü
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Oturumu silme (çıkış yapma)
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
