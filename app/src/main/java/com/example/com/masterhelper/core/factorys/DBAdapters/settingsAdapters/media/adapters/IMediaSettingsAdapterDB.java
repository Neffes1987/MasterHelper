package com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media.adapters;

import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;

public interface IMediaSettingsAdapterDB {
  ModelList get(int parent);
  void update(String [] paths, int parent);
  String[] pathsToArray(ModelList list);
  ModelList getModelsByPaths(String[] paths, ModelList list);
}
