package com.example.masterhelper.ui.journey;

import android.content.Context;
import android.database.Cursor;
import com.example.masterhelper.data.DbHelpers;
import com.example.masterhelper.data.contracts.JourneysContract;
import com.example.masterhelper.models.JourneyModel;

import java.util.LinkedHashMap;

public class JourneyDBAdapter {
  /** класс для работы с базой */
  private DbHelpers dbHelpers;

  /** выбранное приключение */
  private JourneyModel currentJourney;

  /** конструкторы */
  JourneyDBAdapter(){
    dbHelpers = new DbHelpers();
  }
  JourneyDBAdapter(int journeyId){
    dbHelpers = new DbHelpers();
    currentJourney = getJourney(journeyId);
  }

  /** получить данные по приключению по его ид */
  private JourneyModel getJourney(int id){
    String sqlQuery = JourneysContract.getListQuery(JourneysContract.TABLE_NAME, null, JourneysContract._ID+"="+ id, null, 1);
    Cursor queryResult = dbHelpers.getList(sqlQuery);
    queryResult.moveToNext();
    int titleColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);
    int idColumnIndex = queryResult.getColumnIndex(JourneysContract._ID);
    return new JourneyModel(queryResult.getString(titleColumnIndex), queryResult.getInt(idColumnIndex));
  }

  /** получить название приключения */
  public String getJourneyTitle(){
    return currentJourney.getTitle();
  }

  /** получить ид приключения */
  public int getJourneyId(){
    return currentJourney.getId();
  }

  /** создать новое приключения */
  public void addJourney(String newItemName){
    String sqlQuery = dbHelpers.journeysContract.addItemQuery(new JourneyModel(newItemName), 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  /** удалить приключение */
  public void deleteJourney(int journeyId){
    String sqlQuery = dbHelpers.journeysContract.deleteItemQuery(journeyId);
    dbHelpers.deleteItem(sqlQuery);
  }

  /** изменить данные по путешествию */
  public void updateJourney(int journeyId, String newTitle){
    String sqlQuery = dbHelpers.journeysContract.updateItemQuery(journeyId, new JourneyModel(newTitle));
    dbHelpers.updateItem(sqlQuery);
  }

  /** получить список приключений */
  public LinkedHashMap<Integer, JourneyModel> getJourneysList(){
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
