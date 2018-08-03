package com.depromeet.onsong.playlist;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Supplier;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.depromeet.media.MusicLibrary;
import com.depromeet.media.domain.Music;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.core.MediaBrowserConnectionCallback;
import com.depromeet.onsong.core.MediaBrowserSubscriptionCallback;
import com.depromeet.onsong.core.MediaControllerCallback;
import com.depromeet.onsong.core.MusicService;
import com.depromeet.onsong.genre.GenreState;
import com.depromeet.onsong.home.HomeActivity;
import com.depromeet.onsong.player.PlayingActivity;
import com.depromeet.onsong.utils.ColorFilter;
import com.groupon.grox.Store;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.depromeet.onsong.core.MusicService.PARAM_MUSIC;
import static com.depromeet.onsong.core.MusicToMetaDataCompat.toMetaData;
import static com.depromeet.onsong.playlist.PlaylistState.SCROLL_BY_SELECTION;
import static com.depromeet.onsong.utils.TransitionUtils.transitionOnBackground;

public class PlaylistActivity extends BaseActivity {
  private static final String TAG = PlaylistActivity.class.getSimpleName();
  private static final String PARAM_GENRE = "genre";

  @BindView(R.id.layout_playlist) ConstraintLayout layoutPlaylist;
  @BindView(R.id.text_title) TextView textTitle;
  @BindView(R.id.text_music_title) TextView textMusicTitle;
  @BindView(R.id.text_music_artist) TextView textMusicArtist;
  @BindView(R.id.text_music_record_time) TextView textMusicRecordTime;
  @BindView(R.id.text_music_length) TextView textMusicLength;
  @BindView(R.id.seekbar_music) SeekBar seekbarMusic;
  @BindView(R.id.image_pause) ImageView imagePause;
  @BindView(R.id.image_like) ImageView imageLike;
  @BindView(R.id.image_next) ImageView imageNext;
  @BindView(R.id.layout_controller) ConstraintLayout layoutController;
  @BindView(R.id.recycler_music) RecyclerView recyclerMusic;

  Store<PlaylistState> playlistStateStore;

  MusicRecyclerAdapter musicRecyclerAdapter;
  SnapHelper musicRecyclerSnapHelper;

  MediaControllerCallback mediaControllerCallback;
  MediaBrowserSubscriptionCallback mediaBrowserSubscriptionCallback;
  MediaBrowserConnectionCallback mediaBrowserConnectionCallback;
  Supplier<MediaBrowserCompat> mediaBrowserSupplier;

  private MediaMetadataCompat mCurrentMetadata;
  private PlaybackStateCompat mCurrentState;

  private MediaBrowserCompat mediaBrowser;

  @Override protected int getLayoutRes() {
    return R.layout.activity_playlist;
  }

  @Override protected void onStart() {
    super.onStart();
    mediaBrowserSupplier = () -> mediaBrowser;

    mediaControllerCallback = new MediaControllerCallback();
    mediaBrowserSubscriptionCallback = new MediaBrowserSubscriptionCallback();
    mediaBrowserConnectionCallback = new MediaBrowserConnectionCallback(
        this, mediaBrowserSupplier, mediaBrowserSubscriptionCallback
    );

    mediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mediaBrowserConnectionCallback, null);

    Disposable disposable1 = mediaBrowserConnectionCallback.onConnectedEventProvider.subscribe(mediaController -> {
      Log.i(TAG, "onStart: 연결 구독자로 옴");
      mediaBrowser.subscribe(mediaBrowser.getRoot(), mediaBrowserSubscriptionCallback);

      updatePlaybackState(mediaController.getPlaybackState());
      updateMetadata(mediaController.getMetadata());

      mediaController.registerCallback(mediaControllerCallback);
      MediaControllerCompat.setMediaController(this, mediaController);
    });

    Disposable disposable2 = mediaControllerCallback.onMetadataChangedEventProvider.subscribe(this::updateMetadata);
    Disposable disposable3 = mediaControllerCallback.onPlaybackStateChangedEventProvider.subscribe(this::updatePlaybackState);
    Disposable disposable4 = mediaControllerCallback.onSessionDestroyedEventProvider.subscribe(aBoolean -> updatePlaybackState(null));
    compositeDisposable.add(disposable1);
    compositeDisposable.add(disposable2);
    compositeDisposable.add(disposable3);
    compositeDisposable.add(disposable4);

    mediaBrowser.connect();
  }

  private void updatePlaybackState(PlaybackStateCompat state) {
    mCurrentState = state;
    Log.i(TAG, "updatePlaybackState: state = " + state);
    if (state == null || state.getState() == PlaybackState.STATE_PAUSED
        || state.getState() == PlaybackState.STATE_STOPPED) {
      imagePause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.btn_play));
    } else {
      imagePause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause));
    }
  }

  private void updateMetadata(MediaMetadataCompat metadata) {
    mCurrentMetadata = metadata;
  }

  @Override
  public void onStop() {
    super.onStop();
    MediaControllerCompat controller = MediaControllerCompat.getMediaController(this);
    if (controller != null) {
      controller.unregisterCallback(mediaControllerCallback);
    }
    if (mediaBrowser != null && mediaBrowser.isConnected()) {
      // FIXME
      if (mCurrentMetadata != null) {
        mediaBrowser.unsubscribe(mCurrentMetadata.getDescription().getMediaId());
      }
      mediaBrowser.disconnect();
    }
  }

  @Override protected void initStore() {
    playlistStateStore = new Store<>(
        new PlaylistState(
            Stream.of(
                new Music("Whatever", "Ugly Duck", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("So what", "Beenzino", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("Whatever", "Ugly Duck", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("So what", "Beenzino", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", "http://depromeet-4th-final.s3.amazonaws.com/music/test.mp3", 60)
            ).collect(Collectors.toList()), 0, PlaylistState.SCROLL_BY_EMPTY
        )
    );
  }

  @Override protected void initView() {
    // placeholder 처리 (실제로는 비동기 요청이 갔다가 왔을떄 렌더링이 될테니)
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    recyclerMusic.setLayoutManager(layoutManager);
    recyclerMusic.setOverScrollMode(View.OVER_SCROLL_NEVER);

    musicRecyclerSnapHelper = new LinearSnapHelper();
    musicRecyclerSnapHelper.attachToRecyclerView(recyclerMusic);

    musicRecyclerAdapter = new MusicRecyclerAdapter(playlistStateStore);
    recyclerMusic.setAdapter(musicRecyclerAdapter);

    recyclerMusic.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
          // filter invalid index
          int position = layoutManager.findLastCompletelyVisibleItemPosition();
          if (0 > position || position >= playlistStateStore.getState().musics.size()) {
            return;
          }

          if (playlistStateStore.getState().chosen != layoutManager.findLastCompletelyVisibleItemPosition()) {
            playlistStateStore.dispatch(new ChooseMusicBySnapAction(layoutManager.findLastCompletelyVisibleItemPosition()));
          }
        }
      }
    });

    compositeDisposable.add(
        musicRecyclerAdapter.onItemClickedEventProvider
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(positionViewPair -> {
              Log.i(TAG, "initView: viewHolder is = " + positionViewPair.second);
              Intent intent = PlayingActivity.intent(this,
                  playlistStateStore.getState().musics.get(positionViewPair.first),
                  positionViewPair.first
              );
              ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                  this,
                  new Pair<>(positionViewPair.second, PlayingActivity.VIEW_NAME_HEADER_IMAGE),
                  new Pair<>(textMusicArtist, PlayingActivity.VIEW_NAME_HEADER_ARTIST),
                  new Pair<>(textMusicTitle, PlayingActivity.VIEW_NAME_HEADER_MUSIC)
              );

              // Now we can start the Activity, providing the activity options as a bundle
              ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
            })
    );

    imageNext.setOnClickListener(v -> startActivity(HomeActivity.intent(this)));
    imagePause.setOnClickListener(v -> {
      Log.i(TAG, "initView: mCurrentState" + mCurrentState);
      final int state = mCurrentState == null ? PlaybackStateCompat.STATE_NONE : mCurrentState.getState();
      if (state == PlaybackState.STATE_PAUSED || state == PlaybackState.STATE_STOPPED || state == PlaybackState.STATE_NONE) {
        playAudio();
      } else {
        MediaControllerCompat.getMediaController(this)
            .getTransportControls()
            .pause();
      }
    });
  }

  @Override protected void subscribeStore() {
    final int[] drawables = new int[]{
        R.drawable.img_album_01, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04,
        R.drawable.img_album_05, R.drawable.img_album_06, R.drawable.img_album_07
    };


    playlistStateStore.subscribe(newState -> {
      Log.i(TAG, "subscribeStore: subscribe" + newState.chosen);

      if (newState.scrollBy.equals(SCROLL_BY_SELECTION)) {
        recyclerMusic.smoothScrollToPosition(newState.chosen);
      }

      textMusicTitle.setText(newState.musics.get(newState.chosen).title);
      textMusicArtist.setText(newState.musics.get(newState.chosen).artist);
//      textMusicRecordTime.setText(newState.musics.get(newState.chosen).title);
//      textMusicLength.setText(String.newState.musics.get(newState.chosen).length); format

      Glide.with(this)
          .asBitmap()
          .load(drawables[newState.chosen % drawables.length])
          .apply(bitmapTransform(new BlurTransformation(70, 2)))
          .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(
                @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
              BitmapDrawable blurredDrawable = new BitmapDrawable(getResources(), resource);
              blurredDrawable.setColorFilter(ColorFilter.applyLightness(24));
              transitionOnBackground(
                  layoutPlaylist,
                  ContextCompat.getDrawable(layoutPlaylist.getContext(), R.color.genreContentsAccent),
                  blurredDrawable
              );
            }
          });
    });
  }

  private void playAudio() {
    Music music = playlistStateStore.getState().musics.get(
        playlistStateStore.getState().chosen
    );

    mCurrentMetadata = toMetaData(this, music);
    updateMetadata(mCurrentMetadata);
    Bundle bundle = new Bundle();
    bundle.putParcelable(PARAM_MUSIC, mCurrentMetadata);

    MediaControllerCompat.getMediaController(this)
        .getTransportControls()
        .playFromUri(Uri.parse(music.getMusicUrl()), bundle);
  }

  public static Intent intent(AppCompatActivity activity, GenreState.GenreColorPair genreColorPair) {
    return new Intent(activity, PlaylistActivity.class)
        .putExtra(PARAM_GENRE, genreColorPair);
  }
}
