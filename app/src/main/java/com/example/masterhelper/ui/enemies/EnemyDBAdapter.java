package com.example.masterhelper.ui.enemies;

import android.content.Context;
import android.database.Cursor;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.EnemyContract;
import com.example.masterhelper.models.EnemyModel;

import java.util.LinkedHashMap;

public class EnemyDBAdapter {
  /** класс для работы с базой */
  private DbHelpers dbHelpers;

  /**  */
  public EnemyDBAdapter(Context context){
    dbHelpers = new DbHelpers(context);
  }

  /**  */
  public LinkedHashMap<Integer, EnemyModel> getEnemiesByScriptId(int scriptId){
    String sqlQuery = EnemyContract.getListQuery(EnemyContract.TABLE_NAME, null, EnemyContract.COLUMN_SCRIPT_ID +" = " + scriptId, EnemyContract.COLUMN_CURRENT_ORDERING, 0);
    LinkedHashMap<Integer, EnemyModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int nameColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_DESCRIPTION);
      int totalHealthColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_TOTAL_HEALTH);
      int currentHealthColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_CURRENT_HEALTH);
      int currentOrderingIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_CURRENT_ORDERING);
      int idColumnIndex = queryResult.getColumnIndex(EnemyContract._ID);

      EnemyModel EnemyModel = new EnemyModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(nameColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getInt(totalHealthColumnIndex),
        queryResult.getInt(currentHealthColumnIndex),
        queryResult.getInt(currentOrderingIndex)
      );
      result.put(EnemyModel.getId(), EnemyModel);
    }
    queryResult.close();
    return result;
  }

  /**  */
  public EnemyModel getEnemyById(int enemyId){
    String sqlQuery = EnemyContract.getListQuery(EnemyContract.TABLE_NAME, null, EnemyContract._ID+"="+ enemyId, EnemyContract._ID + " DESC", 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    EnemyModel enemyModel = null;
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_TITLE);
      int descriptionColumnIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_DESCRIPTION);
      int idColumnIndex = queryResult.getColumnIndex(EnemyContract._ID);
      int totalHealthIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_TOTAL_HEALTH);
      int currentHealthIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_CURRENT_HEALTH);
      int currentOrderingIndex = queryResult.getColumnIndex(EnemyContract.COLUMN_CURRENT_ORDERING);

      enemyModel = new EnemyModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        queryResult.getInt(totalHealthIndex),
        queryResult.getInt(currentHealthIndex),
        queryResult.getInt(currentOrderingIndex)
      );
    } 
    return enemyModel;
  }
  

  /**  */
  public void addNewEnemy(EnemyModel newItem, int scriptId){
    String sqlQuery = dbHelpers.enemyContract.addItemQuery(newItem, scriptId);
    dbHelpers.addNewItem(sqlQuery);
  }

  /**  */
  public void updateEnemy(EnemyModel newItem){
    String sqlQuery = dbHelpers.enemyContract.updateItemQuery(newItem.getId(), newItem);
    dbHelpers.updateItem(sqlQuery);
  }

  /**  */
  public void deleteEnemy(int enemyID){
    String sqlQuery = dbHelpers.enemyContract.deleteItemQuery(enemyID);
    dbHelpers.deleteItem(sqlQuery);
  }
}
