package com.depromeet.media;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.AudioAttributesCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import java.util.List;

public class MusicPlayerService extends MediaBrowserServiceCompat {
  private MediaSessionCompat mediaSessionCompat;
  private MediaControllerCompat mediaControllerCompat;
  private NotificationManagerCompat notificationManagerCompat;
  private MediaSessionConnector mediaSessionConnector;

  private ExoPlayer exoPlayer;

  private boolean isForegroundService = false;

  private AudioAttributesCompat audioAttributes = new AudioAttributesCompat.Builder()
      .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
      .setUsage(AudioAttributesCompat.USAGE_MEDIA)
      .build();

  @Override public void onCreate() {
    super.onCreate();

    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    exoPlayer = new AudioFocusExoPlayerDecorator(audioAttributes,
        audioManager,
        ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
            new DefaultTrackSelector(),
            new DefaultLoadControl()));
  }

  @Nullable @Override
  public BrowserRoot onGetRoot(
      @NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
    return null;
  }

  @Override
  public void onLoadChildren(
      @NonNull String parentId,
      @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

  }
}
