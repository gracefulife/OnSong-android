package com.depromeet.onsong.player;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.depromeet.onsong.Apps;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.domain.Music;
import com.depromeet.onsong.utils.BetweenActivityTransitionListener;
import com.depromeet.onsong.utils.ColorFilter;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.depromeet.onsong.utils.TransitionUtils.transitionOnBackground;

public class PlayingActivity extends BaseActivity {
  private static final String TAG = PlayingActivity.class.getSimpleName();

  final int[] drawables = new int[]{
      R.drawable.img_album_01, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04,
      R.drawable.img_album_05, R.drawable.img_album_06, R.drawable.img_album_07
  };

  public static final String PARAM_MUSIC = "music";
  public static final String PARAM_TEMP_POSITION = "position"; // TODO 네트워크 연결 후 삭제
  public static final String VIEW_NAME_HEADER_IMAGE = "image";
  public static final String VIEW_NAME_HEADER_MUSIC = "music";
  public static final String VIEW_NAME_HEADER_ARTIST = "artist";

  @BindView(R.id.image_album_cover) ImageView imageAlbumCover;
  @BindView(R.id.image_prev) ImageView imagePrev;
  @BindView(R.id.image_music_info) ImageView imageMusicInfo;
  @BindView(R.id.image_like) ImageView imageLike;
  @BindView(R.id.text_music_title) TextView textMusicTitle;
  @BindView(R.id.text_music_artist) TextView textMusicArtist;
  @BindView(R.id.image_play_or_pause) ImageView imagePlayOrPause;
  @BindView(R.id.image_prev_music) ImageView imagePrevMusic;
  @BindView(R.id.image_next_music) ImageView imageNextMusic;
  @BindView(R.id.layout_player) ConstraintLayout layoutPlayer;
  @BindView(R.id.layout_background) ConstraintLayout layoutBackground;

  Music chosenMusic;
  int position;

  @Override protected int getLayoutRes() {
    return R.layout.activity_playing;
  }

  @Override protected void initStore() {

  }

  @Override protected void initView() {
    // set transition
    ViewCompat.setTransitionName(imageAlbumCover, VIEW_NAME_HEADER_IMAGE);
    ViewCompat.setTransitionName(textMusicTitle, VIEW_NAME_HEADER_MUSIC);
    ViewCompat.setTransitionName(textMusicArtist, VIEW_NAME_HEADER_ARTIST);

    // load bundle data
    chosenMusic = (Music) getIntent().getSerializableExtra(PARAM_MUSIC);
    position = getIntent().getIntExtra(PARAM_TEMP_POSITION, 0);

    // init view
    textMusicTitle.setText(chosenMusic.title);
    textMusicArtist.setText(chosenMusic.artist);

    Glide.with(this)
        .asBitmap()
        .load(drawables[position % drawables.length])
        .apply(bitmapTransform(new BlurTransformation(70, 2)))
        .into(new SimpleTarget<Bitmap>() {
          @Override
          public void onResourceReady(
              @NonNull Bitmap resource, @Nullable
              com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
            BitmapDrawable blurredDrawable = new BitmapDrawable(getResources(), resource);
            blurredDrawable.setColorFilter(ColorFilter.applyLightness(36));
            transitionOnBackground(
                layoutBackground,
                ContextCompat.getDrawable(layoutBackground.getContext(), R.color.genreContentsAccent),
                blurredDrawable
            );
          }
        });

    if (addTransitionListener()) {
      loadThumbnail();
      return;
    }
    loadFullSizeImage();
  }

  @Override protected void subscribeStore() {
    int volumeControlStream = AudioManager.STREAM_MUSIC;
    Apps.get().mediaSession();
  }

  private boolean addTransitionListener() {
    final Transition transition = getWindow().getSharedElementEnterTransition();

    if (transition != null) {
      transition.addListener(new BetweenActivityTransitionListener(this::loadFullSizeImage));
      return true;
    }

    return false;
  }

  private void loadThumbnail() {
    // TODO thumbnail
    Glide.with(imageAlbumCover)
        .load(drawables[position % drawables.length])
        .into(imageAlbumCover);
  }

  private void loadFullSizeImage() {
    Glide.with(imageAlbumCover)
        .load(drawables[position % drawables.length])
        .into(imageAlbumCover);
  }

  public static Intent intent(AppCompatActivity activity, Music music, int position) {
    return new Intent(activity, PlayingActivity.class).putExtra(PARAM_MUSIC, music).putExtra(PARAM_TEMP_POSITION, position);
  }
}
