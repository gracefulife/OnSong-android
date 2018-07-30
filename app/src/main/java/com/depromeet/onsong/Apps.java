package com.depromeet.onsong;

import android.app.Application;
import android.content.ComponentName;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import com.depromeet.onsong.core.MediaSessionConnection;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mindorks.nybus.NYBus;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class Apps extends Application {
  private static Apps instance;

  private NYBus eventBus;
  private RxSharedPreferences preferences;
  private MediaSessionConnection mediaSessionConnection;

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

  public final MediaSessionConnection mediaSession() {
    // FIXME 사용 시나리오에 따라 수정필요함
    try {
      return mediaSessionConnection == null ? createMediaSession() : mediaSessionConnection;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  MediaSessionConnection createMediaSession() throws RemoteException {
    return new MediaSessionConnection(this, new ComponentName(this, com.example.android.uamp.media.MusicServiceKt.class));
  }

}
