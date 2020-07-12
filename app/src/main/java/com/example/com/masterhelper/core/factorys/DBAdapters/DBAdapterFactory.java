package com.example.com.masterhelper.core.factorys.DBAdapters;

import com.example.com.masterhelper.core.factorys.DBAdapters.adapters.*;

public class DBAdapterFactory {
  public static CommonBDAdapter getAdapter(AdaptersType type){
    switch (type){
      case script: return new ScriptDBAdapter();
      case scene: return new SceneDBAdapter();
      case enemy: return new EnemyDBAdapter();
      case ability: return new AbilityDBAdapter();
      case journey: return new JourneyDBAdapter();
      default: return null;
    }
  }
}
