package com.depromeet.onsong.core;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaSessionConnection extends MediaBrowserCompat.ConnectionCallback {
  public Context context;
  public ComponentName componentName;

  public PlayerState playerState;

  public MediaControllerCompat.TransportControls transportControls;
  public MediaBrowserCompat mediaBrowserCompat;
  public MediaControllerCompat mediaControllerCompat;

  public MediaBrowserConnectionCallback mediaBrowserConnectionCallback;
  public MediaControllerCallback mediaControllerCallback;

  public MediaSessionConnection(Context context, ComponentName componentName) {
    this.context = context;
    this.componentName = componentName;

    mediaBrowserConnectionCallback = new MediaBrowserConnectionCallback();
    mediaControllerCallback = new MediaControllerCallback();

    mediaBrowserCompat = new MediaBrowserCompat(context, componentName, mediaBrowserConnectionCallback, null);
    mediaBrowserCompat.connect();
  }

  public void subscribe(String parentId, MediaBrowserCompat.SubscriptionCallback callback) {
    mediaBrowserCompat.subscribe(parentId, callback);
  }

  public void unsubscribe(String parentId, MediaBrowserCompat.SubscriptionCallback callback) {
    mediaBrowserCompat.unsubscribe(parentId, callback);
  }

  public class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
    @Override public void onConnected() {
      super.onConnected();

      try {
        mediaControllerCompat = new MediaControllerCompat(context, mediaBrowserCompat.getSessionToken());
        mediaControllerCompat.registerCallback(mediaControllerCallback);

        transportControls = mediaControllerCompat.getTransportControls();

        playerState = new PlayerState(false, mediaBrowserCompat.getRoot());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override public void onConnectionSuspended() {
      super.onConnectionSuspended();
    }

    @Override public void onConnectionFailed() {
      super.onConnectionFailed();
    }
  }

  @RequiredArgsConstructor
  public class MediaControllerCallback extends MediaControllerCompat.Callback {
    @Override public void onSessionDestroyed() {
      super.onSessionDestroyed();
    }

    @Override public void onPlaybackStateChanged(PlaybackStateCompat state) {
      super.onPlaybackStateChanged(state);
    }

    @Override public void onMetadataChanged(MediaMetadataCompat metadata) {
      super.onMetadataChanged(metadata);
    }

    @Override public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
      super.onQueueChanged(queue);
    }
  }
}
