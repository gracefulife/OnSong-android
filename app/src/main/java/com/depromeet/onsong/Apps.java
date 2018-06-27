package com.depromeet.onsong;

import android.app.Application;

public class Apps extends Application {
  private static Apps instance;

  @Override public void onCreate() {
    super.onCreate();

    instance = this;
  }

  public static Apps get() {
    return instance;
  }

}
