package com.masterhelper.mediaworker;

/** плеер фоновой музыки в сценах и в скриптах */
public class BackgroundMediaPlayer implements IBackgroundMediaPlayer{

  /**  */
  private static MediaPlayerAdapter mediaPlayer;

  /** конструктор плеера */
  public BackgroundMediaPlayer(){
    mediaPlayer = new MediaPlayerAdapter(false);
  }

  /**  */
  public void setMediaList(String newMediaList) {
    mediaPlayer.setMediaList(newMediaList);
  }

  /**  */
  public void startMediaList(){
    mediaPlayer.startMediaList();
  }

  /**  */
  public void stopMediaList(){
    mediaPlayer.stopMediaList();
  }

}
