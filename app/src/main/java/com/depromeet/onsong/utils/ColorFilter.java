package com.depromeet.onsong.utils;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

public abstract class ColorFilter {
  private static final String TAG = ColorFilter.class.getSimpleName();

  public static PorterDuffColorFilter applyLightness(int progress) {
    Log.i(TAG, "applyLightness: progress = " + progress);

    if (progress > 0) {
      int value = progress * 255 / 100;
      Log.i(TAG, "applyLightness: value = " + value);
      return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
    } else {
      int value = (progress * -1) * 255 / 100;
      return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
    }
  }
}
