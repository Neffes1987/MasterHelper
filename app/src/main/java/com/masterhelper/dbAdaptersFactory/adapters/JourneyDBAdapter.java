package com.masterhelper.dbAdaptersFactory.adapters;

import android.database.Cursor;
import com.masterhelper.appconfig.DbHelpers;
import com.masterhelper.appconfig.GlobalApplication;
import com.masterhelper.appconfig.contracts.JourneysContract;
import com.example.masterhelper.models.JourneyModel;

import java.util.LinkedHashMap;

public class JourneyDBAdapter extends CommonBDAdapter<JourneyModel> {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();

  /** конструкторы */
  public JourneyDBAdapter(){}

  /** получить данные по приключению по его ид */
  @Override
  public JourneyModel get(int id) {
    String sqlQuery = JourneysContract.getListQuery(JourneysContract.TABLE_NAME, null, JourneysContract._ID+"="+ id, null, 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
  }

  @Override
  public void add(JourneyModel newItem, int parentId) {
    String sqlQuery = dbHelpers.journeysContract.addItemQuery(newItem, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  /** удалить приключение */
  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.journeysContract.deleteItemQuery(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  /** изменить данные по путешествию */
  @Override
  public void update(JourneyModel updatedModel) {
    String sqlQuery = dbHelpers.journeysContract.updateItemQuery(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }

  /** получить список приключений */
  @Override
  public LinkedHashMap<Integer, JourneyModel> getListByParentId(int parentId) {
    String sqlQuery = JourneysContract.getListQuery(JourneysContract.TABLE_NAME, null, null, JourneysContract._ID + " DESC", 0);
    LinkedHashMap<Integer, JourneyModel> result = new LinkedHashMap<>();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
      int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
      JourneyModel journeyModel = new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
      result.put(journeyModel.getId(),journeyModel);
    }
    queryResult.close();
    return result;
  }
}
