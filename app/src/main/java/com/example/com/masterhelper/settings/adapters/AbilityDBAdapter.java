package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import android.util.Log;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.AbilityModel;
import com.example.com.masterhelper.core.contracts.settings.AbilitiesContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class AbilityDBAdapter extends AbstractSetting {
  public AbilityDBAdapter(){}

  @Override
  public void add(DataModel newModel, int parentId) {
    AbilityModel model = (AbilityModel) newModel;
    String sqlQuery = dbHelpers.abilitiesContract.add(model, parentId);
    dbHelpers.addNewItem(sqlQuery);
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
    Log.i("TAG", "update: " + id);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.abilitiesContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
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
