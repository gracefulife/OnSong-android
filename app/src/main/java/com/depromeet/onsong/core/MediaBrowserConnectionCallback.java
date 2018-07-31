package com.depromeet.onsong.core;

import android.app.Activity;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;

import com.annimon.stream.function.Supplier;

import io.reactivex.subjects.PublishSubject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
  @NonNull Activity activity;
  @NonNull Supplier<MediaBrowserCompat> mediaBrowserProvider;
  @NonNull MediaBrowserCompat.SubscriptionCallback subscriptionCallback;

  public final PublishSubject<MediaControllerCompat> onConnectedEventProvider = PublishSubject.create();

  MediaBrowserCompat mediaBrowser;

  @Override
  public void onConnected() {
    mediaBrowser = mediaBrowserProvider.get();

    mediaBrowser.subscribe(mediaBrowser.getRoot(), subscriptionCallback);
    try {
      MediaControllerCompat mediaController =
          new MediaControllerCompat(activity, mediaBrowser.getSessionToken());
//      updatePlaybackState(mediaController.getPlaybackState());
//      updateMetadata(mediaController.getMetadata());
//      mediaController.registerCallback(mMediaControllerCallback);
      MediaControllerCompat.setMediaController(activity, mediaController);

      onConnectedEventProvider.onNext(mediaController);
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
  }
}
