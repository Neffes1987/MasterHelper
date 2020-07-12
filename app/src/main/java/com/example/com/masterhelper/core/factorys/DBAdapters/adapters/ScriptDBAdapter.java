package com.example.com.masterhelper.core.factorys.DBAdapters.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.appconfig.DbHelpers;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;
import com.example.com.masterhelper.core.appconfig.contracts.SceneContract;
import com.example.com.masterhelper.core.appconfig.contracts.ScriptMusicContract;
import com.example.com.masterhelper.core.appconfig.contracts.ScriptsContract;
import com.example.com.masterhelper.core.appconfig.models.ScriptModel;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ScriptDBAdapter extends CommonBDAdapter<ScriptModel> {
  /**  */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /**  */
  public ScriptDBAdapter(){}

  public HashMap<String, Integer> getMediaForScript(int scriptId){
    String sqlQuery = ScriptMusicContract.getListQuery(ScriptMusicContract.TABLE_NAME, null, ScriptMusicContract.COLUMN_SCRIPT_ID+"="+ scriptId, SceneContract._ID + " DESC", 0);
    HashMap<String, Integer> scriptMedia = new HashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int pathColumnIndex = queryResult.getColumnIndex(ScriptMusicContract.COLUMN_FILE_PATH);
      int idColumnIndex = queryResult.getColumnIndex(ScriptMusicContract._ID);
      scriptMedia.put(queryResult.getString(pathColumnIndex), queryResult.getInt(idColumnIndex));
    }
    queryResult.close();
    return scriptMedia;
  }

  /** обновить медиа для сцены */
  public void updateScriptMedia(String[] paths, int scriptId){
    HashMap<String, Integer> currentPaths = getMediaForScript(scriptId);
    if(paths == null){
      return;
    }
    for (String path: paths ) {
      if(!currentPaths.containsKey(path) && !path.equals("")){
        String sqlQuery = dbHelpers.scriptMusicContract.addItemQuery(path, scriptId);
        dbHelpers.addNewItem(sqlQuery);
      } else {
        currentPaths.remove(path);
      }
    }


    if(currentPaths.size() > 0){
      String sqlQuery = dbHelpers.scriptMusicContract.deleteRecordsByIds(currentPaths.values().toString().replaceAll("\\[|\\]|\\s", ""));
      dbHelpers.addNewItem(sqlQuery);
    }
  }

  @Override
  public ScriptModel get(int id) {
    String sqlQuery = ScriptsContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract._ID+"="+ id, ScriptsContract._ID + " DESC", 0);
    ScriptModel result = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(ScriptsContract._ID);
      int isFinishedColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_IS_FINISHED);
      int isBattleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_HAS_BATTLE_EVENT);

      result = new ScriptModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getString(isBattleColumnIndex).equals("true"),
        queryResult.getString(isFinishedColumnIndex).equals("true")
      );
    }
    queryResult.close();
    return result;
  }

  @Override
  public void add(ScriptModel newItem, int parentId) {
    String sqlQuery = dbHelpers.scriptsContract.addItemQuery(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.scriptsContract.deleteItemQuery(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(ScriptModel updatedModel) {
    String sqlQuery = dbHelpers.scriptsContract.updateItemQuery(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public LinkedHashMap<Integer, ScriptModel> getListByParentId(int parentId) {
    String sqlQuery = ScriptsContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract.COLUMN_SCENE_ID+"="+ parentId, null, 0);
    LinkedHashMap<Integer, ScriptModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(ScriptsContract._ID);
      int isFinishedColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_IS_FINISHED);
      int isBattleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_HAS_BATTLE_EVENT);

      ScriptModel scriptModel = new ScriptModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getString(isBattleColumnIndex).equals("true"),
        queryResult.getString(isFinishedColumnIndex).equals("true")
      );
      result.put(scriptModel.getId(), scriptModel);
    }
    queryResult.close();
    return result;
  }
}
