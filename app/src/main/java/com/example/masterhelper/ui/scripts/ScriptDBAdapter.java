package com.example.masterhelper.ui.scripts;

import android.content.Context;
import android.database.Cursor;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.ScriptsContract;
import com.example.masterhelper.models.ScriptRecycleDataModel;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class ScriptDBAdapter {
  /**  */
  private DbHelpers dbHelpers;

  /**  */
  public ScriptDBAdapter(Context context){
    dbHelpers = new DbHelpers(context);
  }

  /**  */
  public LinkedHashMap<Integer, ScriptRecycleDataModel> getScriptsList(int sceneId){
    String sqlQuery = ScriptsContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract.COLUMN_SCENE_ID+"="+ sceneId, ScriptsContract._ID + " DESC", 0);
    LinkedHashMap<Integer, ScriptRecycleDataModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(ScriptsContract._ID);
      int isFinishedColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_IS_FINISHED);
      int isBattleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_HAS_BATTLE_EVENT);

      ScriptRecycleDataModel scriptRecycleDataModel = new ScriptRecycleDataModel(
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(idColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getString(isBattleColumnIndex).equals("true"),
        queryResult.getString(isFinishedColumnIndex).equals("true")
      );
      result.put(scriptRecycleDataModel.getId(), scriptRecycleDataModel);
    }
    queryResult.close();
    return result;
  }

  /**  */
  public ScriptRecycleDataModel getScriptById(int scriptId){
    String sqlQuery = ScriptsContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract._ID+"="+ scriptId, ScriptsContract._ID + " DESC", 0);
    ScriptRecycleDataModel result = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(ScriptsContract._ID);
      int isFinishedColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_IS_FINISHED);
      int isBattleColumnIndex = queryResult.getColumnIndex(ScriptsContract.COLUMN_HAS_BATTLE_EVENT);

      result = new ScriptRecycleDataModel(
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

  /**  */
  public void addNewScript(ScriptRecycleDataModel newItem, int sceneId){
    String sqlQuery = dbHelpers.scriptsContract.addItemQuery(newItem, sceneId);
    dbHelpers.addNewItem(sqlQuery);
  }

  /**  */
  public void updateScript(ScriptRecycleDataModel newItem, int itemId){
    String sqlQuery = dbHelpers.scriptsContract.updateItemQuery(itemId, newItem);
    dbHelpers.updateItem(sqlQuery);
  }

  /**  */
  public void deleteScript(int itemId){
    String sqlQuery = dbHelpers.scriptsContract.deleteItemQuery(itemId);
    dbHelpers.deleteItem(sqlQuery);
  }
}
