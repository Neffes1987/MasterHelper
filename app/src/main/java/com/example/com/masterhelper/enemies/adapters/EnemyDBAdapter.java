package com.example.com.masterhelper.enemies.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.DBadapters.CommonBDAdapter;
import com.example.com.masterhelper.enemies.contracts.EnemyContract;
import com.example.com.masterhelper.enemies.models.EnemyModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class EnemyDBAdapter  extends CommonBDAdapter<EnemyModel> {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /**  */
  public EnemyDBAdapter(){}

  @Override
  public EnemyModel get(int id) {
    String whereCondition = id > -1 ? EnemyContract._ID+"="+ id : null;
    String sqlQuery = GeneralContract.getListQuery(EnemyContract.TABLE_NAME, null, whereCondition, EnemyContract._ID + " DESC", 1);
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
    String sqlQuery = dbHelpers.enemyContract.add(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.enemyContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(EnemyModel updatedModel) {
    String sqlQuery = dbHelpers.enemyContract.update(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public ModelList getListByParentId(int parentId) {
    String sqlQuery = GeneralContract.getListQuery(EnemyContract.TABLE_NAME, null, EnemyContract.COLUMN_SCRIPT_ID +" = " + parentId, EnemyContract.COLUMN_CURRENT_ORDERING, 0);
    ModelList result = new ModelList();
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
      result.addToList(EnemyModel);
    }
    queryResult.close();
    return result;
  }
}
