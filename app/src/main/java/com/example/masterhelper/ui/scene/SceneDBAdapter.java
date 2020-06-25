package com.example.masterhelper.ui.scene;

import android.content.Context;
import android.database.Cursor;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.SceneContract;
import com.example.masterhelper.models.SceneRecycleDataModel;

import java.util.LinkedHashMap;

public class SceneDBAdapter {
  /** хелпер для работы с базой */
  private DbHelpers dbHelpers;

  /** конструктор */
  public SceneDBAdapter(Context context){
    dbHelpers = new DbHelpers(context);
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

      SceneRecycleDataModel sceneRecycleDataModel = new SceneRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        0,
        0,
        false,
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
        false,
        false
      );
    }
    queryResult.close();
    return sceneRecycleDataModel;
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
