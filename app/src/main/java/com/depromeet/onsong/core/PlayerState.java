package com.depromeet.onsong.core;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import lombok.ToString;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_NONE;

@ToString
public class PlayerState {
  public static final PlaybackStateCompat EMPTY_PLAYBACK_STATE;
  public static final MediaMetadataCompat NOTHING_PLAYING;

  static {
    EMPTY_PLAYBACK_STATE = new PlaybackStateCompat.Builder().setState(STATE_NONE, 0, 0f).build();
    NOTHING_PLAYING = new MediaMetadataCompat.Builder().putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "")
        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0)
        .build();
  }

  public final boolean isConnected;
  public final String mediaId;
  public final PlaybackStateCompat playbackState;
  public final MediaMetadataCompat mediaMetadataCompat;

  public PlayerState(boolean isConnected, String mediaId) {
    this.isConnected = isConnected;
    this.mediaId = mediaId;
    this.playbackState = EMPTY_PLAYBACK_STATE;
    this.mediaMetadataCompat = NOTHING_PLAYING;
  }
}
