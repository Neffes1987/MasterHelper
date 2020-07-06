package com.example.masterhelper.mediaworker;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.masterhelper.GlobalApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/** Singleton для плеера фоновой музыки в сценах и в скриптах */
public final class BackgroundMediaPlayer {

  private static HashSet<String> mediaList = new HashSet<>();
  private static int currentMedia = 0;

  /** экземпляр плеера */
  private static BackgroundMediaPlayer backgroundMediaPlayerInstance;

  private static MediaPlayer mediaPlayer;

  /** конструктор плеера */
  private BackgroundMediaPlayer(){
    mediaPlayer = new MediaPlayer();
    mediaPlayer.setOnCompletionListener(v -> {
      mediaPlayer.reset();
      startNextMediaFile();
    });
  }

  /** метод для получения созданного экземпляра плеера */
  @RequiresApi(api = Build.VERSION_CODES.M)
  public static BackgroundMediaPlayer getInstance(){
    if(backgroundMediaPlayerInstance == null){
      backgroundMediaPlayerInstance = new BackgroundMediaPlayer();
    }

    return backgroundMediaPlayerInstance;
  }

  public void startMediaRecord(File file) {
    try {
      Log.i("TAG", "startMediaRecord: " + file.getPath());
      mediaPlayer.setDataSource(GlobalApplication.getAppContext(), Uri.fromFile(file));
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mediaPlayer.prepare();
      mediaPlayer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setCurrentMedia(int currentMediaItem) {
    if(currentMediaItem > mediaList.size()-1){
      currentMedia = 0;
      return;
    }
    currentMedia = currentMediaItem;
  }

  public int getCurrentMedia() {
    return currentMedia;
  }

  public void setMediaList(String newMediaList) {
    mediaList.clear();
    String[] list = newMediaList.split(",");
    mediaList.addAll(Arrays.asList(list));
  }

  public HashSet<String> getMediaList() {
    return mediaList;
  }

  private void startNextMediaFile(){
    if(getMediaList().size() == 0){
      return;
    }

    setCurrentMedia(getCurrentMedia()+1);
    String filePath = (String) getMediaList().toArray()[getCurrentMedia()];
    File file = new File(filePath);
    startMediaRecord(file);
  }

  public void startMediaList(){
    if(mediaPlayer.isPlaying()){
      mediaPlayer.stop();
    }
    if(mediaPlayer.getDuration() > 0 ){
      mediaPlayer.start();
      return;
    }
    startNextMediaFile();
  }

  public void stopMediaList(){
    mediaPlayer.stop();
  }

}
