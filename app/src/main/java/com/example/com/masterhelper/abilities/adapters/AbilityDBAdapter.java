package com.example.com.masterhelper.abilities.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.abilities.models.AbilityModel;
import com.example.com.masterhelper.abilities.contracts.AbilitiesContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;

public class AbilityDBAdapter extends AbstractSetting {
  public AbilityDBAdapter(){}

  @Override
  public int add(DataModel newModel, int parentId) {
    AbilityModel model = (AbilityModel) newModel;
    String sqlQuery = dbHelpers.abilitiesContract.add(model, parentId);
    dbHelpers.addNewItem(sqlQuery);
    return get(0).getId();
  }

  @Override
  public void create(String name, String description, int parentId) {
    AbilityModel model = new AbilityModel(name);
    add(model, parentId);
  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {

  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {
    AbilityModel model = new AbilityModel(id, name, 0);
    String sqlQuery = dbHelpers.abilitiesContract.update(id, model);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.abilitiesContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  public DataModel get(int id) {
    String where = null;
    if(id != 0){
      where = AbilitiesContract._ID+"="+ id;
    }
    String sqlQuery = GeneralContract.getListQuery(AbilitiesContract.TABLE_NAME, null, where, AbilitiesContract._ID + " DESC", 1);
    AbilityModel AbilityModel = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(AbilitiesContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(AbilitiesContract._ID);

      AbilityModel = new AbilityModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        0
      );
    }
    queryResult.close();
    return AbilityModel;
  }

  @Override
  public ModelList list() {
    String sqlQuery = GeneralContract.getListQuery(AbilitiesContract.TABLE_NAME, null, null, AbilitiesContract._ID + " DESC", 0);
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
  public ModelList list(int parentId) {
    return list();
  }
}
