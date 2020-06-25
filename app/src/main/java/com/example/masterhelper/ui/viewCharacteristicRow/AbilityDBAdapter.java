package com.example.masterhelper.ui.viewCharacteristicRow;

import android.content.Context;
import android.database.Cursor;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.AbilitiesContract;
import com.example.masterhelper.models.AbilityModel;

import java.util.LinkedHashMap;

public class AbilityDBAdapter {
  /** класс для работы с базой */
  private DbHelpers dbHelpers;

  public AbilityDBAdapter(Context context){
    dbHelpers = new DbHelpers(context);
  }

  /** создать новое приключения */
  public void addAbility(String newItemName){
    String sqlQuery = dbHelpers.abilitiesContract.addItemQuery(new AbilityModel(newItemName), 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  public void addAbilityByEnemyId(AbilityModel newItem, int scriptId){
    String sqlQuery = dbHelpers.enemyAbilitiesContract.addItemQuery(newItem, scriptId);
    dbHelpers.addNewItem(sqlQuery);
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
  
}
