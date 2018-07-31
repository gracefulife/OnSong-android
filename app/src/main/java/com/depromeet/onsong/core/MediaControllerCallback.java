package com.depromeet.onsong.core;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import io.reactivex.subjects.PublishSubject;

public class MediaControllerCallback extends MediaControllerCompat.Callback {
  public final PublishSubject<MediaMetadataCompat> onMetadataChangedEventProvider = PublishSubject.create();
  public final PublishSubject<PlaybackStateCompat> onPlaybackStateChangedEventProvider = PublishSubject.create();
  public final PublishSubject<Boolean> onSessionDestroyedEventProvider = PublishSubject.create();

  @Override
  public void onMetadataChanged(MediaMetadataCompat metadata) {
    onMetadataChangedEventProvider.onNext(metadata);
  }

  @Override
  public void onPlaybackStateChanged(PlaybackStateCompat state) {
    onPlaybackStateChangedEventProvider.onNext(state);
  }

  @Override
  public void onSessionDestroyed() {
    onSessionDestroyedEventProvider.onNext(true);
  }
}
