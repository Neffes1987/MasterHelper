package com.example.com.masterhelper.core.factorys.DBAdapters.settingsAdapters.media.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.appconfig.contracts.SceneContract;
import com.example.com.masterhelper.core.appconfig.contracts.ScriptMusicContract;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.SoundFileModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;


public class ScriptMediaSettings extends MediaSettings {
  @Override
  public ModelList get(int scriptId) {
    String sqlQuery = ScriptMusicContract.getListQuery(ScriptMusicContract.TABLE_NAME, null, ScriptMusicContract.COLUMN_SCRIPT_ID+"="+ scriptId, SceneContract._ID + " DESC", 0);
    ModelList scriptMedia = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int pathColumnIndex = queryResult.getColumnIndex(ScriptMusicContract.COLUMN_FILE_PATH);
      int idColumnIndex = queryResult.getColumnIndex(ScriptMusicContract._ID);
      scriptMedia.addToList(new SoundFileModel(queryResult.getString(pathColumnIndex), queryResult.getInt(idColumnIndex)));
    }
    queryResult.close();
    return scriptMedia;
  }

  @Override
  public void update(String[] paths, int sceneId) {
    ModelList list = get(sceneId);
    ModelList currentPaths = getModelsByPaths(paths, list);
    for (DataModel model: currentPaths.values()) {
      SoundFileModel sound = (SoundFileModel) model;
      if(!list.containsKey(model.getId())){
        String sqlQuery = dbHelpers.scriptMusicContract.addItemQuery(sound.getPath(), sceneId);
        dbHelpers.addNewItem(sqlQuery);
      } else {
        list.remove(model.getId());
      }
    }

    if(list.size() > 0){
      String sqlQuery = dbHelpers.scriptMusicContract.deleteRecordsByIds(list.idsToString());
      dbHelpers.addNewItem(sqlQuery);
    }
  }
}
