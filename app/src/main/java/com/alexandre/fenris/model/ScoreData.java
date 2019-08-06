package com.alexandre.fenris.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoreData {

    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCE";
    private static final String SCORE_DATA = "SCORE_DATA";

    public static ArrayList<ItemRowScore> mItemRowScore = new ArrayList<>();

    public ScoreData() {
    }

    public static void loadData(Context activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SCORE_DATA, null);
        Type type = new TypeToken<ArrayList<ItemRowScore>>() {
        }.getType();
        mItemRowScore = gson.fromJson(json, type);

        if (mItemRowScore == null) {
            mItemRowScore = new ArrayList<>();
        }
    }

    public static void clearData(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
