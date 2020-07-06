package com.example.masterhelper.ui.viewCharacteristicRow;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.AbilitiesContract;
import com.example.masterhelper.data.contracts.EnemyAbilitiesContract;
import com.example.masterhelper.models.AbilityModel;

import java.util.LinkedHashMap;

public class AbilityDBAdapter {
  /** класс для работы с базой */
  private DbHelpers dbHelpers;

  public AbilityDBAdapter(){
    dbHelpers = new DbHelpers();
  }

  /** создать новое приключения */
  public void addAbility(String newItemName){
    String sqlQuery = dbHelpers.abilitiesContract.addItemQuery(new AbilityModel(newItemName), 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  public void addAbilitiesByEnemyId(LinkedHashMap<Integer, AbilityModel> newItems, int enemyId){
    StringBuilder updateQuery = new StringBuilder();
    StringBuilder deleteQuery = new StringBuilder();

    LinkedHashMap<Integer, AbilityModel> existedAbilities = getSettingsAbilitiesListByEnemy(enemyId);

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
      for (AbilityModel deletedItem : existedAbilities.values()) {
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


    Log.i("TAG", "addAbilitiesByEnemyId: " + updateQuery );

    if (updateQuery.toString().length() > 0){
      dbHelpers.updateItem(updateQuery.toString());
    }
  }

  /** создать новое приключения */
  public void removeAbility(int id){
    String sqlQuery = dbHelpers.abilitiesContract.deleteItemQuery(id);
    dbHelpers.deleteItem(sqlQuery);
  }

  /**  */
  public LinkedHashMap<Integer, AbilityModel> getSettingsAbilitiesList(){
    String sqlQuery = AbilitiesContract.getListQuery(AbilitiesContract.TABLE_NAME, null, null, AbilitiesContract._ID + " DESC", 0);
    LinkedHashMap<Integer, AbilityModel> result = new LinkedHashMap<>();
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
      result.put(AbilityModel.getId(), AbilityModel);
    }
    queryResult.close();
    return result;
  }

  /**  */
  public LinkedHashMap<Integer, AbilityModel> getSettingsAbilitiesListByEnemy(int enemyId){
    LinkedHashMap<Integer, AbilityModel> result = new LinkedHashMap<>();

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
        .append(enemyId)
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
      result.put(AbilityModel.getId(), AbilityModel);
    }
    queryResult.close();
    return result;
  }
  
}
