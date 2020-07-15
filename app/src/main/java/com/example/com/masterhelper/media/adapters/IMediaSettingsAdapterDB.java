package com.example.com.masterhelper.media.adapters;

import com.example.com.masterhelper.core.models.utilities.ModelList;

public interface IMediaSettingsAdapterDB {
  ModelList get(int parent);
  void update(String [] paths, int parent);
  String[] pathsToArray(ModelList list);
  ModelList getModelsByPaths(String[] paths, ModelList list);
}
