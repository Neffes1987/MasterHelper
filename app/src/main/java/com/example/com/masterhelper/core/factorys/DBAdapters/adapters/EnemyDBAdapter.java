package com.example.com.masterhelper.core.factorys.DBAdapters.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.appconfig.DbHelpers;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;
import com.example.com.masterhelper.core.appconfig.contracts.EnemyContract;
import com.example.com.masterhelper.core.appconfig.models.EnemyModel;

import java.util.LinkedHashMap;

public class EnemyDBAdapter  extends CommonBDAdapter<EnemyModel>  {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /**  */
  public EnemyDBAdapter(){}

  @Override
  public EnemyModel get(int id) {
    String whereCondition = id > -1 ? EnemyContract._ID+"="+ id : null;
    String sqlQuery = EnemyContract.getListQuery(EnemyContract.TABLE_NAME, null, whereCondition, EnemyContract._ID + " DESC", 1);
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

  @Override
  public void add(EnemyModel newItem, int parentId) {
    String sqlQuery = dbHelpers.enemyContract.addItemQuery(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.enemyContract.deleteItemQuery(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(EnemyModel updatedModel) {
    String sqlQuery = dbHelpers.enemyContract.updateItemQuery(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public LinkedHashMap<Integer, EnemyModel> getListByParentId(int parentId) {
    String sqlQuery = EnemyContract.getListQuery(EnemyContract.TABLE_NAME, null, EnemyContract.COLUMN_SCRIPT_ID +" = " + parentId, EnemyContract.COLUMN_CURRENT_ORDERING, 0);
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
}
