package com.depromeet.onsong.core;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;

import com.depromeet.media.domain.Music;

public abstract class MusicToMetaDataCompat {
  public static @NonNull MediaMetadataCompat toMetaData(
      @NonNull Context context, @NonNull Music music) {
    MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
    builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, music.musicUrl);
    builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, music.getTitle()); // TODO album
    builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, music.getArtist());
    builder.putString(MediaMetadataCompat.METADATA_KEY_GENRE, music.getGenreName());
    builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, music.getTitle());
    builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, music.getLength());
    builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART,
        BitmapFactory.decodeResource(context.getResources(), com.depromeet.media.R.drawable.album_jazz_blues)
    );
    return builder.build();
  }
}
