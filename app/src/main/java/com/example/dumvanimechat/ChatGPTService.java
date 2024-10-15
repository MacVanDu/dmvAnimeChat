package com.example.dumvanimechat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class ChatGPTService {
    private static final String DUMV_API_KEY =  "";
    private static final String DUMV_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;
    private String characterName;

    private List<TinNhan> tinNhanHistory;


    private String insertMultipleEmojis(String text, String[] selectedEmojis, int numberOfEmojis) {
        Random random = new Random();
        for (int i = 0; i < numberOfEmojis; i++) {
            int randomIndex = random.nextInt(selectedEmojis.length);
            String emoji = selectedEmojis[randomIndex];

            int insertPosition;
            do {
                insertPosition = random.nextInt(text.length() + 1);
            } while (insertPosition > 0 && text.charAt(insertPosition - 1) != ' ');

            text = text.substring(0, insertPosition) + emoji + text.substring(insertPosition);
        }
        return text;
    }


    private String addEmojiToContent(String text) {
        String[] positive_cam_xuc = {"😀", "😄", "😆", "😊"};
        String[] negative_cam_xuc = {"😞", "😔", "😢", "😩"};
        String[] neutral_cam_xuc = {"😐", "😶", "😑", "🙂"};
        String[] surprise_cam_xuc = {"😲", "😯", "😧", "😮"};
        String[] fallback_cam_xuc = {"😀"};

        String[] positive_tu_khoa = {"happy", "excited", "glad", "joy"};
        String[] negative_tu_khoa = {"sad", "angry", "upset", "disappointed"};
        String[] neutral_tu_khoa = {"neutral", "calm", "okay"};
        String[] surprise_tu_khoa = {"surprised", "shocked", "amazed"};

        String[] emojis = null;

        for (String keyword : positive_tu_khoa) {
            if (text.toLowerCase().contains(keyword)) {
                emojis = positive_cam_xuc;
                break;
            }
        }

        if (emojis == null) {
            for (String keyword : negative_tu_khoa) {
                if (text.toLowerCase().contains(keyword)) {
                    emojis = negative_cam_xuc;
                    break;
                }
            }
        }

        if (emojis == null) {
            for (String keyword : neutral_tu_khoa) {
                if (text.toLowerCase().contains(keyword)) {
                    emojis = neutral_cam_xuc;
                    break;
                }
            }
        }

        if (emojis == null) {
            for (String keyword : surprise_tu_khoa) {
                if (text.toLowerCase().contains(keyword)) {
                    emojis = surprise_cam_xuc;
                    break;
                }
            }
        }

        if (emojis == null) {
            emojis = fallback_cam_xuc;
        }

        Random random = new Random();
        int soEmojis = random.nextInt(4) + 1;
        return insertMultipleEmojis(text, emojis, soEmojis);
    }



    public void setCharacter(String characterName) {
        this.characterName = characterName;
    }

    public void addToMessageHistory(TinNhan tinNhan) {
        tinNhanHistory.add(tinNhan);
    }


    public ChatGPTService() {
        client = new OkHttpClient();
        tinNhanHistory = new ArrayList<>();
    }

    public String getChatResponse(String message) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();



        JSONArray messages = new JSONArray();

        if (characterName != null) {
            JSONObject systemMessage = new JSONObject();
            try {
                systemMessage.put("role", "system");
                systemMessage.put("content",  characterName + " say ");
                messages.put(systemMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (TinNhan msg : tinNhanHistory) {
            JSONObject historyMessage = new JSONObject();
            try {
                historyMessage.put("role", msg.isNguoi() ? "user" : "assistant");
                historyMessage.put("content", msg.getText());
                messages.put(historyMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject userMessage = new JSONObject();
        try {
            userMessage.put("role", "user");
            userMessage.put("content", message);
            messages.put(userMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(JSON, requestBody.toString());
        Request request = new Request.Builder()
                .url(DUMV_API_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + DUMV_API_KEY)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject choice = choices.getJSONObject(0);
            JSONObject responseMessage = choice.getJSONObject("message");
            String content = responseMessage.getString("content");

            if (new Random().nextInt(100) < 50) { // 20% chance of inserting an emoji
                content = addEmojiToContent(content);
            }

            return content;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
