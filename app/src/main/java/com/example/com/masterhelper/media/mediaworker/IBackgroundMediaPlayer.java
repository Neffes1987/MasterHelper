package com.example.com.masterhelper.media.mediaworker;

public interface IBackgroundMediaPlayer  {
  /** указать список воспроизведения */
  static void setMediaList(String newMediaList){}

  /** запустить лист для проигрывания */
  static void startMediaList() {}

  /** отстановить воспроизведение */
  static void stopMediaList(){}
}
