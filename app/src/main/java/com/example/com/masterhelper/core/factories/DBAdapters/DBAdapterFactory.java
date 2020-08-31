package com.example.com.masterhelper.core.factories.DBAdapters;

import com.example.com.masterhelper.core.factories.DBAdapters.adapters.*;
import com.example.com.masterhelper.scene.adapters.ScriptDBAdapter;

public class DBAdapterFactory {
  public static CommonBDAdapter getAdapter(AdaptersType type){
    switch (type){
      case ability: return new AbilityDBAdapter();
      default: return null;
    }
  }
}
