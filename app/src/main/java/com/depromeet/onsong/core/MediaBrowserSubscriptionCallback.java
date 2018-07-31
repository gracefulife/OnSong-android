package com.depromeet.onsong.core;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback {
  public final PublishSubject<List<MediaBrowserCompat.MediaItem>> onChildrenLoadedProvider = PublishSubject.create();

  @Override
  public void onChildrenLoaded(
      @NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
    onChildrenLoadedProvider.onNext(children);
  }
}
