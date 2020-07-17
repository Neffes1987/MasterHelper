package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.settings.GoalContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class GoalDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.goalContract;

  @Override
  public void add(DataModel newModel) {
    String sqlQuery = contract.add(newModel, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void create(String name, String description) {
    DataModel model = new DataModel();
    model.initGeneralFields(0, name, description);
    add(model);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = contract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public ModelList list() {
    String sqlQuery = GeneralContract.getListQuery(GoalContract.TABLE_NAME, null, null, GoalContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int descriptionColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(GoalContract._ID);

      DataModel model = new DataModel();
        model.initGeneralFields(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex)
      );
      result.addToList(model);
    }
    queryResult.close();
    return result;
  }
}
