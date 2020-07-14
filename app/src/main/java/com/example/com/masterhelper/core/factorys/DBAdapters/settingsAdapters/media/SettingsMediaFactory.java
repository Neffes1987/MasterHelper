package com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media;

import com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media.adapters.MediaSettings;
import com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media.adapters.SceneMediaSettings;
import com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media.adapters.ScriptMediaSettings;

public class SettingsMediaFactory {
  public static MediaSettings getAdapter(SettingsAdapterType type){
    switch (type){
      case scene: return new SceneMediaSettings();
      case script: return new ScriptMediaSettings();
      default: return null;
    }
  }
}
