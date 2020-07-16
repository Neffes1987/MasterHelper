package com.example.com.masterhelper.core.factories.DBAdapters.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.journey.JourneysContract;
import com.example.com.masterhelper.core.models.JourneyModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;

public class JourneyDBAdapter extends CommonBDAdapter<JourneyModel> {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /** конструкторы */
  public JourneyDBAdapter(){}

  /** получить данные по приключению по его ид */
  @Override
  public JourneyModel get(int id) {
    String sqlQuery = GeneralContract.getListQuery(JourneysContract.TABLE_NAME, null, JourneysContract._ID+"="+ id, null, 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
  }

  @Override
  public void add(JourneyModel newItem, int parentId) {
    String sqlQuery = dbHelpers.journeysContract.add(newItem, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  /** удалить приключение */
  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.journeysContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  /** изменить данные по путешествию */
  @Override
  public void update(JourneyModel updatedModel) {
    String sqlQuery = dbHelpers.journeysContract.update(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  /** получить список приключений */
  @Override
  public ModelList getListByParentId(int parentId) {
    String sqlQuery = GeneralContract.getListQuery(JourneysContract.TABLE_NAME, null, null, JourneysContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
      int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
      JourneyModel journeyModel = new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
      result.addToList(journeyModel);
    }
    queryResult.close();
    return result;
  }
}
