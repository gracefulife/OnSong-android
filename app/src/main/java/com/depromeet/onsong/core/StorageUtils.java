package com.depromeet.onsong.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;

import com.depromeet.onsong.domain.Music;
import com.google.gson.Gson;

import java.util.List;

public class StorageUtils {


  private final String STORAGE = " STORAGE";
  private SharedPreferences preferences;
  private Context context;

  public StorageUtils(Context context) {
    this.context = context;
  }

  public void storeAudio(List<MediaStore.Audio> arrayList) {
    preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = preferences.edit();
    Gson gson = new Gson();
    String json = gson.toJson(arrayList);
    editor.putString("audioArrayList", json);
    editor.apply();
  }

  public List<Music> loadAudio() {
    preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    Gson gson = new Gson();
    String json = preferences.getString("audioArrayList", null);
    Type type = new TypeToken<List<MediaStore.Audio>>() {
    }.getType();
    return gson.fromJson(json, type);
  }

  public void storeAudioIndex(int index) {
    preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt("audioIndex", index);
    editor.apply();
  }

  public int loadAudioIndex() {
    preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    return preferences.getInt("audioIndex", -1);//return -1 if no data found
  }

  public void clearCachedAudioPlaylist() {
    preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.commit();
  }
}
