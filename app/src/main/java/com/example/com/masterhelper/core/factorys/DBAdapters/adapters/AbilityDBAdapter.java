package com.example.com.masterhelper.core.factorys.DBAdapters.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.appconfig.DbHelpers;
import com.example.com.masterhelper.core.appconfig.GlobalApplication;
import com.example.com.masterhelper.core.appconfig.contracts.AbilitiesContract;
import com.example.com.masterhelper.core.appconfig.contracts.EnemyAbilitiesContract;
import com.example.com.masterhelper.core.appconfig.models.AbilityModel;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.appconfig.models.utilities.ModelList;

import java.util.LinkedHashMap;

public class AbilityDBAdapter extends CommonBDAdapter<AbilityModel> {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();


  public AbilityDBAdapter(){}

  public void addAbilitiesByEnemyId(LinkedHashMap<Integer, AbilityModel> newItems, int enemyId){
    StringBuilder updateQuery = new StringBuilder();
    StringBuilder deleteQuery = new StringBuilder();

    ModelList existedAbilities = getListByParentId(enemyId);

    for (AbilityModel newItem: newItems.values()) {
      int newItemId = newItem.getId();
      if(existedAbilities.get(newItemId) == null){
        dbHelpers.addNewItem(dbHelpers.enemyAbilitiesContract.addItemQuery(newItem, enemyId));
      } else {
        updateQuery.append(dbHelpers.enemyAbilitiesContract.updateItemQuery(enemyId, newItem))
          .append(" WHERE "+ EnemyAbilitiesContract.COLUMN_ABILITY_ID)
          .append("=")
          .append(newItemId)
          .append(";");
        existedAbilities.remove(newItemId);
      }
    }

    if(existedAbilities.size() > 0){
      deleteQuery.append("DELETE FROM " + EnemyAbilitiesContract.TABLE_NAME + " WHERE " + EnemyAbilitiesContract.COLUMN_ABILITY_ID + " IN (");
      int idx = 0;
      for (DataModel deletedItem : existedAbilities.values()) {
           deleteQuery.append(deletedItem.getId());
          if(idx < existedAbilities.size() - 1){
            deleteQuery.append(",");
          }
         idx+=1;
      }
      deleteQuery.append(") AND ")
        .append(EnemyAbilitiesContract.COLUMN_ENEMY_ID + "=")
        .append(enemyId)
        .append(";");
      dbHelpers.deleteItem(deleteQuery.toString());
    }

    if (updateQuery.toString().length() > 0){
      dbHelpers.updateItem(updateQuery.toString());
    }
  }

  /**  */
  public ModelList getSettingsAbilitiesList(){
    String sqlQuery = AbilitiesContract.getListQuery(AbilitiesContract.TABLE_NAME, null, null, AbilitiesContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(AbilitiesContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(AbilitiesContract._ID);

      AbilityModel AbilityModel = new AbilityModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        0
      );
      result.addToList(AbilityModel);
    }
    queryResult.close();
    return result;
  }


  @Override
  public AbilityModel get(int id) {
    return null;
  }

  @Override
  public void add(AbilityModel newItem, int parentId) {
    String sqlQuery = dbHelpers.abilitiesContract.addItemQuery(newItem, parentId);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.abilitiesContract.deleteItemQuery(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public void update(AbilityModel updatedModel) {

  }

  @Override
  public ModelList getListByParentId(int parentId) {
    ModelList result = new ModelList();

    StringBuilder abilitiesByScriptQuery = new StringBuilder();
    abilitiesByScriptQuery
      .append("SELECT ")
      .append(EnemyAbilitiesContract.TABLE_NAME + "." + EnemyAbilitiesContract.COLUMN_ABILITY_ID + ",")
      .append(EnemyAbilitiesContract.TABLE_NAME + "." + EnemyAbilitiesContract.COLUMN_ABILITY_VALUE + ",")
      .append(AbilitiesContract.TABLE_NAME + "." + AbilitiesContract.COLUMN_NAME)
      .append(" FROM ")
      .append(EnemyAbilitiesContract.TABLE_NAME)
      .append(" INNER JOIN ")
      .append(AbilitiesContract.TABLE_NAME)
      .append(" ON ")
      .append(EnemyAbilitiesContract.TABLE_NAME + "." + EnemyAbilitiesContract.COLUMN_ABILITY_ID)
      .append("=")
      .append(AbilitiesContract.TABLE_NAME + "." + AbilitiesContract._ID)
      .append(" WHERE ")
      .append(EnemyAbilitiesContract.COLUMN_ENEMY_ID)
      .append("=")
      .append(parentId)
    ;
    Cursor queryResult = dbHelpers.getList(abilitiesByScriptQuery.toString());

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(AbilitiesContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(EnemyAbilitiesContract.COLUMN_ABILITY_ID);
      int valueColumnIndex = queryResult.getColumnIndex(EnemyAbilitiesContract.COLUMN_ABILITY_VALUE);

      AbilityModel AbilityModel = new AbilityModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getInt(valueColumnIndex)
      );
      result.addToList(AbilityModel);
    }
    queryResult.close();
    return result;
  }
}
