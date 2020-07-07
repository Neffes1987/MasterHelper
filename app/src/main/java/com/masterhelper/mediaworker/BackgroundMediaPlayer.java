package com.masterhelper.mediaworker;

/** Singleton для плеера фоновой музыки в сценах и в скриптах */
public final class BackgroundMediaPlayer implements IBackgroundMediaPlayer{

  /** экземпляр плеера */
  private static BackgroundMediaPlayer backgroundMediaPlayerInstance;

  /**  */
  private static MediaPlayerAdapter mediaPlayer;

  /** конструктор плеера */
  private BackgroundMediaPlayer(){
    mediaPlayer = new MediaPlayerAdapter(false);
  }

  /** метод для получения созданного экземпляра плеера */
  public static BackgroundMediaPlayer getInstance(){
    if(backgroundMediaPlayerInstance == null){
      backgroundMediaPlayerInstance = new BackgroundMediaPlayer();
    }

    return backgroundMediaPlayerInstance;
  }

  /**  */
  public static void setMediaList(String newMediaList) {
    mediaPlayer.setMediaList(newMediaList);
  }

  /**  */
  public static void startMediaList(){
    mediaPlayer.startMediaList();
  }

  /**  */
  public static void stopMediaList(){
    mediaPlayer.stopMediaList();
  }

}
