package com.depromeet.onsong;

import android.app.Application;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mindorks.nybus.NYBus;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class Apps extends Application {
  private static Apps instance;

  private NYBus eventBus;
  private RxSharedPreferences preferences;

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

  public final NYBus eventBus() {
    return eventBus == null ? eventBus = NYBus.get() : eventBus;
  }

  public final RxSharedPreferences preferences() {
    return preferences == null ? preferences = createPreferences() : preferences;
  }

  RxSharedPreferences createPreferences() {
    return RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(this));
  }
}
