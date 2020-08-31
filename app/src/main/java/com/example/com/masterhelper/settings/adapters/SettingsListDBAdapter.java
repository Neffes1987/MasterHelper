package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.contracts.SettingsContract;
import com.example.com.masterhelper.settings.models.SettingModel;

public class SettingsListDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.settingsContract;

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

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {}

  public int create(String name, String description, String type) {
    SettingModel model = new SettingModel(name, description, type);
    return add(model, 0);
  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {}

  public void update(int id, String name, String description, String type) {
    SettingModel model = new SettingModel(id, name, description, type);
    update(model);
  }

  public SettingModel getDataFromCursor(Cursor queryResult){
    // Используем индекс для получения строки или числа
    int typeColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_TYPE);
    int descriptionColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_DESCRIPTION);
    int positiveColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_POSITIVE_RESULT);
    int negativeColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_NEGATIVE_RESULT);
    int titleColumnIndex = queryResult.getColumnIndex(SettingsContract.COLUMN_NAME);
    int idColumnIndex = queryResult.getColumnIndex(SettingsContract._ID);

    return new SettingModel(
      queryResult.getInt(idColumnIndex),
      queryResult.getString(titleColumnIndex),
      queryResult.getString(descriptionColumnIndex),
      queryResult.getString(typeColumnIndex),
      queryResult.getString(positiveColumnIndex),
      queryResult.getString(negativeColumnIndex)
    );
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = contract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public ModelList list() {
    return null;
  }

  public DataModel get(int id) {
    String where = null;
    if(id != 0){
      where = SettingsContract._ID+"="+ id;
    }
    SettingModel model = null;
    String sqlQuery = GeneralContract.getListQuery(SettingsContract.TABLE_NAME, null, where, SettingsContract._ID + " DESC", 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      model = getDataFromCursor(queryResult);
    }
    queryResult.close();
    return model;
  }

  public ModelList list(String type) {
    String where = SettingsContract.COLUMN_TYPE + "='" + type+"'";
    String sqlQuery = GeneralContract.getListQuery(SettingsContract.TABLE_NAME, null, where, SettingsContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      SettingModel model = getDataFromCursor(queryResult);
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
