package com.depromeet.onsong.playlist;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.depromeet.onsong.BaseActivity;
import com.depromeet.onsong.R;
import com.depromeet.onsong.utils.ColorFilter;
import com.groupon.grox.Store;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PlaylistActivity extends BaseActivity {
  private static final String TAG = PlaylistActivity.class.getSimpleName();

  @BindView(R.id.layout_playlist) ConstraintLayout layoutPlaylist;

  Store<PlaylistState> playlistStateStore;


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
                new Music("XXX", "Kendrick Lamar", "HipHop", "", 60)
            ).collect(Collectors.toList()), 0
        )
    );
  }

  @Override protected void initView() {
    // placeholder 처리 (실제로는 비동기 요청이 갔다가 왔을떄 렌더링이 될테니)

  }

  @Override protected void subscribeStore() {
    playlistStateStore.subscribe(newState -> {
      Log.i(TAG, "subscribeStore: subscribe");

      Glide.with(this)
          .asBitmap()
          .load(R.drawable.img_album)
          .apply(bitmapTransform(new BlurTransformation(70, 2)))
          .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(
                @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
              BitmapDrawable background = new BitmapDrawable(getResources(), resource);
              background.setColorFilter(ColorFilter.applyLightness(50));
              layoutPlaylist.setBackground(background);
            }
          });
    });
  }
}
