package com.example.com.masterhelper.core.appconfig;

import android.app.Application;
import android.content.Context;
import com.example.com.masterhelper.media.mediaworker.BackgroundMediaPlayer;

public class GlobalApplication extends Application {

  private static Context appContext;
  private static DbHelpers dbHelpers;
  private static BackgroundMediaPlayer backgroundMediaPlayer;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = getApplicationContext();
    dbHelpers = new DbHelpers(appContext);
    backgroundMediaPlayer = new BackgroundMediaPlayer();
  }

  public static Context getAppContext() {
    return appContext;
  }

  public static DbHelpers getDbHelpers() {
    return dbHelpers;
  }

  public static BackgroundMediaPlayer getBackgroundMediaPlayer() {
    return backgroundMediaPlayer;
  }
}
