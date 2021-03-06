package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.settings.contracts.AdvanceContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.force.models.AdvanceModel;
import com.example.com.masterhelper.force.models.RelationModal;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;

public class AdvanceDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.advanceContract;

  @Override
  public int add(DataModel newModel, int parentId) {
    String sqlQuery = contract.add(newModel, parentId);
    dbHelpers.addNewItem(sqlQuery);
    return get(0).getId();
  }

  public void update(DataModel newModel) {
    String sqlQuery = contract.update(newModel.getId(), newModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public void create(String name, String description, int parentId) {}

  private RelationModal.DirectionType getEnumBySelection(String[] selectedItems){
    RelationModal.DirectionType type;
    String selectedItem = selectedItems[0];
    String advance = GlobalApplication.getAppContext().getResources().getString(R.string.force_advantages_title);
    if(selectedItem.equals(advance)){
      type = RelationModal.DirectionType.advantage;
    } else {
      type = RelationModal.DirectionType.disadvantage;
    }
    return type;
  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {
    RelationModal.DirectionType type = getEnumBySelection(selectedItems);
    AdvanceModel model = new AdvanceModel(0, name, description, type);
    add(model, parentId);
  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {
    RelationModal.DirectionType type = getEnumBySelection(selectedItems);
    AdvanceModel model = new AdvanceModel(id, name, description, type);
    update(model);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = contract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  public DataModel get(int id) {
    String where = null;
    if(id != 0){
      where = AdvanceContract._ID+"="+ id;
    }
    AdvanceModel model = null;
    String sqlQuery = GeneralContract.getListQuery(AdvanceContract.TABLE_NAME, null, where, AdvanceContract._ID + " DESC", 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int typeColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_TYPE);
      int descriptionColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(AdvanceContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(AdvanceContract._ID);

      model = new AdvanceModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex),
        RelationModal.DirectionType.valueOf(queryResult.getString(typeColumnIndex))
      );
    }
    queryResult.close();
    return model;
  }

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

  @Override
  public ModelList list(int parentId) {
    return list();
  }
}
