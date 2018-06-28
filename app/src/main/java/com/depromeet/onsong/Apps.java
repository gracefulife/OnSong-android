package com.depromeet.onsong;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class Apps extends Application {
  private static Apps instance;

  @Override public void onCreate() {
    super.onCreate();

    instance = this;

    ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoSansKR-Light-Hestia.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());
  }

  public static Apps get() {
    return instance;
  }

}
