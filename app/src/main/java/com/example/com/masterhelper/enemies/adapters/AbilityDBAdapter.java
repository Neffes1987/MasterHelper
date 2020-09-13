package com.example.com.masterhelper.enemies.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.appbar.AppBarFragment;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.DBadapters.CommonBDAdapter;
import com.example.com.masterhelper.enemies.contracts.EnemyAbilitiesContract;
import com.example.com.masterhelper.enemies.models.AbilityModel;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.contracts.SettingsContract;

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
      if(!existedAbilities.containsKey(newItemId)){
        dbHelpers.addNewItem(dbHelpers.enemyAbilitiesContract.add(newItem, enemyId));
      } else {
        updateQuery.append(dbHelpers.enemyAbilitiesContract.update(enemyId, newItem))
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
    String sqlQuery = GeneralContract.getListQuery(SettingsContract.TABLE_NAME, null, SettingsContract.COLUMN_TYPE +"='" + AppBarFragment.RecordType.ability.name()+"'", SettingsContract.COLUMN_NAME + " ASC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(SettingsContract._ID);

      DataModel AbilityModel = new AbilityModel(
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

  }

  @Override
  public void delete(int deletedId) {

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
      .append(SettingsContract.TABLE_NAME + "." + SettingsContract.COLUMN_NAME)
      .append(" FROM ")
      .append(EnemyAbilitiesContract.TABLE_NAME)
      .append(" INNER JOIN ")
      .append(SettingsContract.TABLE_NAME)
      .append(" ON ")
      .append(EnemyAbilitiesContract.TABLE_NAME + "." + EnemyAbilitiesContract.COLUMN_ABILITY_ID)
      .append("=")
      .append(SettingsContract.TABLE_NAME + "." + SettingsContract._ID)
      .append(" WHERE ")
      .append(EnemyAbilitiesContract.COLUMN_ENEMY_ID)
      .append("=")
      .append(parentId)
    ;
    Cursor queryResult = dbHelpers.getList(abilitiesByScriptQuery.toString());

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_NAME);
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
