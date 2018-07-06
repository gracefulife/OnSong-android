package com.depromeet.onsong.playlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.detail.PlayingActivity;
import com.depromeet.onsong.domain.Music;
import com.depromeet.onsong.genre.GenreState;
import com.depromeet.onsong.utils.ColorFilter;
import com.groupon.grox.Store;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
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

  @Override protected int getLayoutRes() {
    return R.layout.activity_playlist;
  }

  @Override protected void initStore() {
    playlistStateStore = new Store<>(
        new PlaylistState(
            Stream.of(
                new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
                new Music("So what", "Beenzino", "HipHop", "", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60),
                new Music("Whatever", "Ugly Duck", "HipHop", "", 60),
                new Music("So what", "Beenzino", "HipHop", "", 60),
                new Music("Seventeen", "Rich Brian", "HipHop", "", 60),
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
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
  }

  @Override protected void subscribeStore() {
    final int[] drawables = new int[]{
        R.drawable.img_album_01, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04,
        R.drawable.img_album_05, R.drawable.img_album_06, R.drawable.img_album_07, R.drawable.img_album_08
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

  public static Intent intent(AppCompatActivity activity, GenreState.GenreColorPair genreColorPair) {
    return new Intent(activity, PlaylistActivity.class)
        .putExtra(PARAM_GENRE, genreColorPair);
  }
}
