package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.settings.AbilitiesContract;
import com.example.com.masterhelper.force.contracts.ForceContract;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;


public class ForceDBAdapter extends AbstractSetting {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
  GeneralContract contract = dbHelpers.forceContract;

  public ForceDBAdapter(){}

  @Override
  public void add(DataModel newModel) {
    String sqlQuery = contract.add(newModel, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void create(String name, String description) {

  }

  @Override
  public void create(String name, String description, String[] selectedItems) {

  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {

  }

  public void update(DataModel model) {
    String sqlQuery = contract.update(model.getId(), model);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void delete(int deletedId) {
    String sqlQuery = contract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public ModelList list() {
    String sqlQuery = GeneralContract.getListQuery(ForceContract.TABLE_NAME, null, null, ForceContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int typeColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_TYPE);
      int descriptionColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_DESCRIPTION);
      int nameColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_NAME);
      int idColumnIndex = queryResult.getColumnIndex(ForceContract._ID);

      ForceModel forceModel = new ForceModel();
      forceModel.setType(ForceModel.ForceType.valueOf(queryResult.getString(typeColumnIndex)));
      forceModel.setId(queryResult.getInt(idColumnIndex));
      forceModel.setDescription(queryResult.getString(descriptionColumnIndex));
      forceModel.setName(queryResult.getString(nameColumnIndex));
      result.addToList(forceModel);
    }
    queryResult.close();
    return result;
  }


  public ForceModel get(int id) {
    String forceByByIdQuery = GeneralContract.getListQuery(ForceContract.TABLE_NAME, null, ForceContract._ID +"="+id, null, 1);
    Cursor queryResult = dbHelpers.getList(forceByByIdQuery);

    ForceModel forceModel = new ForceModel();

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int idColumnIndex = queryResult.getColumnIndex(ForceContract._ID);
      int titleColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_NAME);
      int descriptionColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_DESCRIPTION);
      int typeColumnIndex = queryResult.getColumnIndex(ForceContract.COLUMN_FORCE_TYPE);

      forceModel.setName(queryResult.getString(titleColumnIndex));
      forceModel.setDescription(queryResult.getString(descriptionColumnIndex));
      forceModel.setId(queryResult.getInt(idColumnIndex));
      forceModel.setType(ForceModel.ForceType.valueOf(queryResult.getString(typeColumnIndex)));
    }
    queryResult.close();
    return forceModel;
  }
}
