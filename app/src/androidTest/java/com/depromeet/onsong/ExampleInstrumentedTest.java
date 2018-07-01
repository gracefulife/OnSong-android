package com.depromeet.onsong;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("com.depromeet.onsong", appContext.getPackageName());

    // image filter
//    Glide.with(this)
//        .asBitmap()
//        .load(R.drawable.img_album)
//        .apply(bitmapTransform(new BlurTransformation(70, 2)))
//        .into(new BitmapImageViewTarget(imageBlur) {
//          @Override
//          public void onResourceReady(
//              @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//            super.onResourceReady(resource, transition);
//            imageBlur.setColorFilter(ColorFilter.applyLightness(50));
//            Palette.from(resource).generate(palette -> {
//              int color = palette.getVibrantColor(ContextCompat.getColor(PlaylistActivity.this, android.R.color.white));
//              layoutPlaylist.setBackgroundColor(color);
//            });
//          }
//        });
  }
}
