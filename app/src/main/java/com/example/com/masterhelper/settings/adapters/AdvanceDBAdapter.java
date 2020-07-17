package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.settings.AdvanceContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class AdvanceDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.advanceContract;

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
    String sqlQuery = GeneralContract.getListQuery(AdvanceContract.TABLE_NAME, null, null, AdvanceContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int descriptionColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(AdvanceContract._ID);

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
