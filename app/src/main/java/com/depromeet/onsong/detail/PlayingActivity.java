package com.depromeet.onsong.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.playlist.Music;
import com.depromeet.onsong.utils.BetweenActivityTransitionListener;
import com.depromeet.onsong.utils.ColorFilter;
import com.depromeet.onsong.widget.SquareFrameLayout;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.depromeet.onsong.utils.TransitionUtils.transitionOnBackground;

public class PlayingActivity extends BaseActivity {
  private static final String TAG = PlayingActivity.class.getSimpleName();

  final int[] drawables = new int[]{
      R.drawable.img_album, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04
  };

  public static final String PARAM_MUSIC = "music";
  public static final String VIEW_NAME_HEADER_IMAGE = "image";
  public static final String VIEW_NAME_HEADER_MUSIC = "music";
  public static final String VIEW_NAME_HEADER_ARTIST = "artist";

  @BindView(R.id.image_album_cover) ImageView imageAlbumCover;
  @BindView(R.id.image_prev) ImageView imagePrev;
  @BindView(R.id.layout_container) SquareFrameLayout layoutContainer;
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

  @Override protected int getLayoutRes() {
    return R.layout.activity_playing;
  }

  @Override protected void initStore() {

  }

  @Override protected void initView() {
    chosenMusic = (Music) getIntent().getSerializableExtra(PARAM_MUSIC);
    ViewCompat.setTransitionName(imageAlbumCover, VIEW_NAME_HEADER_IMAGE);

    Glide.with(this)
        .asBitmap()
        .load(drawables[0])
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
        .load(drawables[0])
        .into(imageAlbumCover);
  }

  private void loadFullSizeImage() {
    Glide.with(imageAlbumCover)
        .load(drawables[0])
        .into(imageAlbumCover);
  }

  public static Intent intent(AppCompatActivity activity, Music music) {
    return new Intent(activity, PlayingActivity.class).putExtra(PARAM_MUSIC, music);
  }
}
