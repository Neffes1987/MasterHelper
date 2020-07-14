package com.example.com.masterhelper.media;

import com.example.com.masterhelper.media.adapters.MediaSettings;
import com.example.com.masterhelper.media.adapters.SceneMediaSettings;
import com.example.com.masterhelper.media.adapters.ScriptMediaSettings;
import com.example.com.masterhelper.media.adapters.SettingsAdapterType;

public class SettingsMediaFactory {
  public static MediaSettings getAdapter(SettingsAdapterType type){
    switch (type){
      case scene: return new SceneMediaSettings();
      case script: return new ScriptMediaSettings();
      default: return null;
    }
  }
}
