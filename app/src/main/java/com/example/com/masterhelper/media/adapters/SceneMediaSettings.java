package com.example.com.masterhelper.media.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.scene.SceneContract;
import com.example.com.masterhelper.core.contracts.scene.SceneMusicContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;


public class SceneMediaSettings extends MediaSettings {
  @Override
  public ModelList get(int sceneId) {
    String sqlQuery = GeneralContract.getListQuery(SceneMusicContract.TABLE_NAME, null, SceneMusicContract.COLUMN_SCENE_ID+"="+ sceneId, SceneContract._ID + " DESC", 0);
    ModelList sceneMedia = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int pathColumnIndex = queryResult.getColumnIndex(SceneMusicContract.COLUMN_FILE_PATH);
      int idColumnIndex = queryResult.getColumnIndex(SceneMusicContract._ID);
      SoundFileModel soundFileModel = new SoundFileModel(
        queryResult.getString(pathColumnIndex), queryResult.getInt(idColumnIndex)
      );
      sceneMedia.addToList(soundFileModel);
    }
    queryResult.close();
    return sceneMedia;
  }

  @Override
  public void update(String[] paths, int sceneId) {
    ModelList list = get(sceneId);
    ModelList currentPaths = getModelsByPaths(paths, list);
    for (DataModel model: currentPaths.values()) {
      SoundFileModel sound = (SoundFileModel) model;
      if(!list.containsKey(model.getId())){
        String sqlQuery = dbHelpers.sceneMusicContract.add(sound, sceneId);
        dbHelpers.addNewItem(sqlQuery);
      } else {
        list.remove(model.getId());
      }
    }

    if(list.size() > 0){
      String sqlQuery = dbHelpers.sceneMusicContract.deleteRecordsByIds(list.idsToString());
      dbHelpers.addNewItem(sqlQuery);
    }
  }
}
