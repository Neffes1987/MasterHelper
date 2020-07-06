package com.example.masterhelper.ui.scene;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.data.contracts.SceneMusicContract;
import com.example.masterhelper.models.SceneRecycleDataModel;
import com.example.masterhelper.models.ScriptRecycleDataModel;
import com.example.masterhelper.ui.scripts.ScriptDBAdapter;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SceneDBAdapter {
  /** хелпер для работы с базой */
  private DbHelpers dbHelpers;
  private ScriptDBAdapter scriptDBAdapter;

  /** конструктор */
  public SceneDBAdapter(){
    dbHelpers = new DbHelpers();
    scriptDBAdapter = new ScriptDBAdapter();
  }



  /** получить список сцен для текущего путешествия */
  public LinkedHashMap<Integer, SceneRecycleDataModel> getScenesList(int journeyId){
    String sqlQuery = SceneContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract.COLUMN_JOURNEY_ID+"="+ journeyId, SceneContract._ID + " DESC", 0);

    LinkedHashMap<Integer, SceneRecycleDataModel> result = new LinkedHashMap<>();


    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);
      int sceneId = queryResult.getInt(idColumnIndex);

      LinkedHashMap<Integer, ScriptRecycleDataModel> scripts = scriptDBAdapter.getScriptsList(sceneId);
      int finishedCounter = 0;
      StringBuilder goals = new StringBuilder();

      for (ScriptRecycleDataModel script: scripts.values() ) {
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
      SceneRecycleDataModel sceneRecycleDataModel = new SceneRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        sceneId,
        description.toString(),
        finishedCounter,
        scripts.size(),
        false
      );
      result.put(sceneRecycleDataModel.getId(), sceneRecycleDataModel);
    }
    queryResult.close();
    return result;
  }

  /** получить данные сцены */
  public SceneRecycleDataModel getSceneById(int sceneId){
    String sqlQuery = SceneContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract._ID+"="+ sceneId, SceneContract._ID + " DESC", 1);
    SceneRecycleDataModel sceneRecycleDataModel = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);

      sceneRecycleDataModel = new SceneRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        0,
        0,
        false
      );
    }
    queryResult.close();
    return sceneRecycleDataModel;
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


  /** создать новую сцену */
  public void addNewScene(SceneRecycleDataModel newItem, int journeyId){
    String sqlQuery = dbHelpers.sceneContract.addItemQuery(newItem, journeyId);
    dbHelpers.addNewItem(sqlQuery);
  }

  /** обновить сцену */
  public void updateScene(SceneRecycleDataModel newItem, int itemId){
    String sqlQuery = dbHelpers.sceneContract.updateItemQuery(itemId, newItem);
    dbHelpers.updateItem(sqlQuery);
  }

  /** удалить сцену */
  public void deleteScene(int itemId){
    String sqlQuery = dbHelpers.sceneContract.deleteItemQuery(itemId);
    dbHelpers.deleteItem(sqlQuery);
  }
}
