package com.masterhelper.dbAdaptersFactory.adapters;

import android.database.Cursor;
import com.masterhelper.appconfig.DbHelpers;
import com.masterhelper.appconfig.GlobalApplication;
import com.masterhelper.appconfig.contracts.SceneContract;
import com.masterhelper.appconfig.contracts.SceneMusicContract;
import com.masterhelper.appconfig.models.SceneModel;
import com.masterhelper.appconfig.models.ScriptModel;
import com.masterhelper.dbAdaptersFactory.AdaptersType;
import com.masterhelper.dbAdaptersFactory.DBAdapterFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SceneDBAdapter  extends CommonBDAdapter<SceneModel> {
  /** хелпер для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
  private ScriptDBAdapter scriptDBAdapter;

  /** конструктор */
  public SceneDBAdapter(){
    scriptDBAdapter = (ScriptDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.script);
  }

  public HashMap<String, Integer> getMediaForScene(int sceneId){
    String sqlQuery = SceneMusicContract.getListQuery(SceneMusicContract.TABLE_NAME, null, SceneMusicContract.COLUMN_SCENE_ID+"="+ sceneId, SceneContract._ID + " DESC", 0);
    HashMap<String, Integer> sceneMedia = new HashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int pathColumnIndex = queryResult.getColumnIndex(SceneMusicContract.COLUMN_FILE_PATH);
      int idColumnIndex = queryResult.getColumnIndex(SceneMusicContract._ID);
      sceneMedia.put(queryResult.getString(pathColumnIndex), queryResult.getInt(idColumnIndex));
    }
    queryResult.close();
    return sceneMedia;
  }

  /** обновить медиа для сцены */
  public void updateSceneMedia(String paths, int sceneId){
    HashMap<String, Integer> currentPaths = getMediaForScene(sceneId);
    if(paths == null){
      return;
    }

    String[] selectedPaths = paths.split(",");
    for (String path: selectedPaths ) {
      if(!currentPaths.containsKey(path) && !path.equals("")){
        String sqlQuery = dbHelpers.sceneMusicContract.addItemQuery(path, sceneId);
        dbHelpers.addNewItem(sqlQuery);
      } else {
        currentPaths.remove(path);
      }
    }


    if(currentPaths.size() > 0){
      String sqlQuery = dbHelpers.sceneMusicContract.deleteRecordsByIds(currentPaths.values().toString().replaceAll("\\[|\\]|\\s", ""));
      dbHelpers.addNewItem(sqlQuery);
    }
  }

  @Override
  public SceneModel get(int id) {
    String sqlQuery = SceneContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract._ID+"="+ id, SceneContract._ID + " DESC", 1);
    SceneModel sceneModel = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);

      sceneModel = new SceneModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        0,
        0,
        false
      );
    }
    queryResult.close();
    return sceneModel;
  }

  @Override
  public void add(SceneModel newItem, int parentId) {
    String sqlQuery = dbHelpers.sceneContract.addItemQuery(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.sceneContract.deleteItemQuery(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(SceneModel updatedModel) {
    String sqlQuery = dbHelpers.sceneContract.updateItemQuery(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public LinkedHashMap<Integer, SceneModel> getListByParentId(int parentId) {
    String sqlQuery = SceneContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract.COLUMN_JOURNEY_ID+"="+ parentId, SceneContract._ID + " DESC", 0);

    LinkedHashMap<Integer, SceneModel> result = new LinkedHashMap<>();


    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);
      int sceneId = queryResult.getInt(idColumnIndex);

      LinkedHashMap<Integer, ScriptModel> scripts = scriptDBAdapter.getListByParentId(sceneId);
      int finishedCounter = 0;
      StringBuilder goals = new StringBuilder();

      for (ScriptModel script: scripts.values() ) {
        boolean isFinished = script.isFinished;
        if(isFinished){
          finishedCounter+=1;
        }
        goals
          .append("\r\n")
          .append("- ")
          .append(script.getTitle())
          .append(isFinished ? " [done]" : "")
          .append("\r\n");
      };

      StringBuilder description =  new StringBuilder();
      description.append(queryResult.getString(descriptionColumnIndex));
      if(goals.length() > 0){
        description
          .append("\r\n").append("\r\n")
          .append("Цели: ")
          .append("\r\n")
          .append(goals);
      }
      SceneModel sceneModel = new SceneModel(
        queryResult.getString(titleColumnIndex),
        sceneId,
        description.toString(),
        finishedCounter,
        scripts.size(),
        false
      );
      result.put(sceneModel.getId(), sceneModel);
    }
    queryResult.close();
    return result;
  }
}