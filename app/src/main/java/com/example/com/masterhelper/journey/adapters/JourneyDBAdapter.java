package com.example.com.masterhelper.journey.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.journey.contracts.JourneysContract;
import com.example.com.masterhelper.journey.models.JourneyModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;

public class JourneyDBAdapter extends AbstractSetting {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
  private GoalDBAdapter goalDBAdapter = new GoalDBAdapter();

  /** конструкторы */
  public JourneyDBAdapter(){}

  /** получить данные по приключению по его ид */
  public JourneyModel get(int id) {
    String sqlQuery = GeneralContract.getListQuery(JourneysContract.TABLE_NAME, null, JourneysContract._ID+"="+ id, null, 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
  }

  @Override
  public int add(DataModel newItem, int parentId) {
    String sqlQuery = dbHelpers.journeysContract.add(newItem, 0);
    dbHelpers.addNewItem(sqlQuery);
    sqlQuery = GeneralContract.getListQuery(JourneysContract.TABLE_NAME, null, null, JourneysContract._ID + " DESC", 1);

    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return queryResult.getInt(idColumnIndex);
  }


  @Override
  public void create(String name, String description, int parentId) {

  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {
    add(new JourneyModel(name, 0), 0);
  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {
    this.update(new JourneyModel(name, id));
  }

  /** удалить приключение */
  @Override
  public void delete(int deletedId) {
    String sqlQuery = dbHelpers.journeysContract.delete(deletedId);
    dbHelpers.deleteItem(sqlQuery);
  }

  @Override
  public ModelList list() {

    String sqlQuery = GeneralContract.getListQuery(JourneysContract.TABLE_NAME, null, null, JourneysContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
      int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
      JourneyModel journeyModel = new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));

      ModelList goals = goalDBAdapter.list(journeyModel.getId());
      String goalsDescription = goals.listToString();
      journeyModel.setDescription(goalsDescription);
      result.addToList(journeyModel);
    }
    queryResult.close();
    return result;
  }

  @Override
  public ModelList list(int parentId) {
    return list();
  }

  /** изменить данные по путешествию */
  public void update(JourneyModel updatedModel) {
    String sqlQuery = dbHelpers.journeysContract.update(updatedModel.getId(), updatedModel);
    dbHelpers.updateItem(sqlQuery);
  }
}
