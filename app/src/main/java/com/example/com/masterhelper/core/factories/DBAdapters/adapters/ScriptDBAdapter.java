package com.example.com.masterhelper.core.factories.DBAdapters.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.enemies.ScriptsContract;
import com.example.com.masterhelper.core.models.ScriptModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class ScriptDBAdapter extends CommonBDAdapter<ScriptModel> {
  /**  */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /**  */
  public ScriptDBAdapter(){}

  @Override
  public ScriptModel get(int id) {
    String sqlQuery = GeneralContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract._ID+"="+ id, ScriptsContract._ID + " DESC", 0);
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
    String sqlQuery = dbHelpers.scriptsContract.add(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.scriptsContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(ScriptModel updatedModel) {
    String sqlQuery = dbHelpers.scriptsContract.update(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public ModelList getListByParentId(int parentId) {
    String sqlQuery = GeneralContract.getListQuery(ScriptsContract.TABLE_NAME, null, ScriptsContract.COLUMN_SCENE_ID+"="+ parentId, null, 0);
    ModelList result = new ModelList();
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
      result.addToList(scriptModel);
    }
    queryResult.close();
    return result;
  }
}
