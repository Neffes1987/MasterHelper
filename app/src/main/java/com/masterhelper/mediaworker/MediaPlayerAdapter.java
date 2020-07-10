package com.masterhelper.mediaworker;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;
import com.masterhelper.appconfig.GlobalApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class MediaPlayerAdapter {

  private MediaPlayer mediaPlayer;

  private HashSet<String> mediaList = new HashSet<>();

  private int currentMedia = 0;

  public MediaPlayerAdapter(boolean isSingle){
    mediaPlayer = new MediaPlayer();
    if(!isSingle){
      mediaPlayer.setOnCompletionListener(v -> {
        mediaPlayer.reset();
        startNextMediaFile();
      });
    }
  }

  private void setCurrentMedia(int currentMediaItem) {
    if(currentMediaItem > mediaList.size()-1){
      currentMedia = 0;
      return;
    }
    currentMedia = currentMediaItem;
  }

  private int getCurrentMedia() {
    return currentMedia;
  }

  public void startMediaRecord(File file) {
    if(mediaPlayer.isPlaying()){
      stopMediaRecord();
    }

    try {
      mediaPlayer.setDataSource(GlobalApplication.getAppContext(), Uri.fromFile(file));
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mediaPlayer.prepare();
      mediaPlayer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stopMediaRecord() {
    mediaPlayer.stop();
    mediaPlayer.reset();
  }

  public HashSet<String> getMediaList() {
    return mediaList;
  }

  public void setMediaList(String[] newMediaList) {
    mediaList.clear();
    mediaList.addAll(Arrays.asList(newMediaList));
  }


  private void startNextMediaFile(){
    if(getMediaList().size() == 0){
      return;
    }

    setCurrentMedia(getCurrentMedia()+1);
    String filePath = (String) getMediaList().toArray()[getCurrentMedia()];
    File file = new File(filePath);
    startMediaRecord(file);
    Toast.makeText(GlobalApplication.getAppContext(), file.getName(), Toast.LENGTH_LONG).show();
  }

  public void startMediaList(){
    stopMediaList();
    startNextMediaFile();
  }

  public void stopMediaList(){
    if(mediaPlayer.isPlaying()){
      stopMediaRecord();
    }
  }
}
