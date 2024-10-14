package com.example.dumvanimechat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentConversationsHelper {
    private static final String PREFERENCES_FILE = "animechat_recent_conversations";
    private static final String RECENT_CONVERSATIONS_KEY = "recent_conversations";

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public RecentConversationsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public void saveRecentConversation(NVAnime NVAnime) {
        List<NVAnime> NVAnimes = getRecentConversations();
        NVAnimes.remove(NVAnime);
        NVAnimes.add(0, NVAnime);
        String json = gson.toJson(NVAnimes);
        sharedPreferences.edit().putString(RECENT_CONVERSATIONS_KEY, json).apply();
    }

    public List<NVAnime> getRecentConversations() {
        String json = sharedPreferences.getString(RECENT_CONVERSATIONS_KEY, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<NVAnime>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }
}
