package com.example.com.masterhelper.scene.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.scene.contracts.SceneContract;
import com.example.com.masterhelper.core.factories.DBAdapters.adapters.ScriptDBAdapter;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.SceneModel;
import com.example.com.masterhelper.core.models.ScriptModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.core.factories.DBAdapters.AdaptersType;
import com.example.com.masterhelper.core.factories.DBAdapters.DBAdapterFactory;
import com.example.com.masterhelper.journey.contracts.JourneysContract;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;

public class SceneDBAdapter extends AbstractSetting {
  /** хелпер для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
  private ScriptDBAdapter scriptDBAdapter;

  /** конструктор */
  public SceneDBAdapter(){
    scriptDBAdapter = (ScriptDBAdapter) DBAdapterFactory.getAdapter(AdaptersType.script);
  }

  public SceneModel get(int id) {
    String sqlQuery = GeneralContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract._ID+"="+ id, SceneContract._ID + " DESC", 1);
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
  public int add(DataModel newItem, int parentId) {
    String sqlQuery = dbHelpers.sceneContract.add(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
    sqlQuery = GeneralContract.getListQuery(SceneContract.TABLE_NAME, null, null, SceneContract._ID + " DESC", 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return queryResult.getInt(idColumnIndex);
  }

  @Override
  public void create(String name, String description, int parentId) {

  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {

  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {

  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.sceneContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public ModelList list() {
    return null;
  }

  @Override
  public ModelList list(int parentId) {
    return null;
  }

  public void update(SceneModel updatedModel) {
    String sqlQuery = dbHelpers.sceneContract.update(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  public ModelList getListByParentId(int parentId) {
    String sqlQuery = GeneralContract.getListQuery(SceneContract.TABLE_NAME, null, SceneContract.COLUMN_JOURNEY_ID+"="+ parentId, null, 0);

    ModelList result = new ModelList();


    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(SceneContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(SceneContract._ID);
      int sceneId = queryResult.getInt(idColumnIndex);

      ModelList scripts = scriptDBAdapter.getListByParentId(sceneId);
      int finishedCounter = 0;
      StringBuilder goals = new StringBuilder();

      for (DataModel model: scripts.values() ) {
        ScriptModel script = (ScriptModel) model;
        boolean isFinished = script.isFinished;
        if(isFinished){
          finishedCounter+=1;
        }
        goals
          .append("\r\n")
          .append("- ")
          .append(script.getName())
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
      result.addToList(sceneModel);
    }
    queryResult.close();
    return result;
  }
}
