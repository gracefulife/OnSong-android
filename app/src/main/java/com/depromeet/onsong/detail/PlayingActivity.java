package com.depromeet.onsong.detail;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.playlist.Music;
import com.depromeet.onsong.utils.BetweenActivityTransitionListener;

import butterknife.BindView;

public class PlayingActivity extends BaseActivity {
  private static final String TAG = PlayingActivity.class.getSimpleName();
  final int[] drawables = new int[]{
      R.drawable.img_album, R.drawable.img_album_02, R.drawable.img_album_03, R.drawable.img_album_04
  };

  public static final String PARAM_MUSIC = "music";
  public static final String VIEW_NAME_HEADER_IMAGE = "image";
//  public static final String VIEW_NAME_HEADER_TITLE = "title";
//  public static final String VIEW_NAME_HEADER_ARTIST = "artist";

  @BindView(R.id.image_album_cover) ImageView imageAlbumCover;

  Music chosenMusic;

  @Override protected int getLayoutRes() {
    return R.layout.activity_playing;
  }

  @Override protected void initStore() {

  }

  @Override protected void initView() {
    chosenMusic = (Music) getIntent().getSerializableExtra(PARAM_MUSIC);
    ViewCompat.setTransitionName(imageAlbumCover, VIEW_NAME_HEADER_IMAGE);

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
