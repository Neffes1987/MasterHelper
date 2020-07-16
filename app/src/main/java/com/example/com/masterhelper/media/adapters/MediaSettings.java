package com.example.com.masterhelper.media.adapters;

import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

import java.util.Arrays;
import java.util.HashSet;

public abstract class MediaSettings implements IMediaSettingsAdapterDB {
  protected DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  public String[] pathsToArray(ModelList list){
    HashSet<String> result = new HashSet<>();
    for (DataModel item: list.values()) {
      SoundFileModel soundFileModel = (SoundFileModel) item;
      result.add(soundFileModel.getPath());
    }
    return result.toArray(new String[0]);
  };

  public ModelList getModelsByPaths(String[] paths, ModelList list){
    ModelList result = new ModelList();
    HashSet<String> currentListPaths = new HashSet<>(Arrays.asList(paths));

    for (DataModel model: list.values()) {
      SoundFileModel pathItem = (SoundFileModel) model;
      if(currentListPaths.contains(pathItem.getPath())){
        result.addToList(pathItem);
        currentListPaths.remove(pathItem.getPath());
      }
    }

    for (String path: currentListPaths) {
      SoundFileModel item = new SoundFileModel(path, result.size());
      result.addToList(item);
    }
    return result;
  };
}
