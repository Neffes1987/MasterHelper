package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.settings.AdvanceContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.forces.AdvanceModel;
import com.example.com.masterhelper.core.models.forces.RelationModal;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;

public class AdvanceDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.advanceContract;

  @Override
  public void add(DataModel newModel) {
    String sqlQuery = contract.add(newModel, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void create(String name, String description) {}

  @Override
  public void create(String name, String description, String[] selectedItems) {
    RelationModal.DirectionType type;
    int selectedItem = GlobalApplication.getAppContext().getResources().getIdentifier(selectedItems[0], "int", GlobalApplication.getAppContext().getPackageName());
    if(selectedItem == R.string.force_advantages_title){
      type = RelationModal.DirectionType.advantage;
    } else {
      type = RelationModal.DirectionType.disadvantage;
    }

    AdvanceModel model = new AdvanceModel(0, name, description, type);
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
      int typeColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_TYPE);
      int descriptionColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(AdvanceContract._ID);

      AdvanceModel model = new AdvanceModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        RelationModal.DirectionType.valueOf(queryResult.getString(typeColumnIndex))
      );

      result.addToList(model);
    }
    queryResult.close();
    return result;
  }
}
