package com.example.com.masterhelper.force.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.DbHelpers;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.force.contracts.ForceContract;
import com.example.com.masterhelper.force.contracts.ForceJourneyContract;
import com.example.com.masterhelper.force.models.ForceModel;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.journey.contracts.JourneysContract;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;

import java.util.ArrayList;


public class ForceDBAdapter extends AbstractSetting {
  /** класс для работы с базой */
  private DbHelpers dbHelpers = GlobalApplication.getDbHelpers();
  GeneralContract contract = dbHelpers.forceContract;
  GeneralContract forceJourneyContract = dbHelpers.forceJourneyContract;

  public ForceDBAdapter(){}

  @Override
  public void add(DataModel newModel, int parentId) {
    String sqlQuery = contract.add(newModel, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  @Override
  public void create(String name, String description, int parentId) {

  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {

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
      forceModel.setInvolvedJourneys(getSelectedJourneys(forceModel.getId()));
      result.addToList(forceModel);
    }
    queryResult.close();
    return result;
  }

  @Override
  public ModelList list(int parentId) {
    return list();
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
      forceModel.setInvolvedJourneys(getSelectedJourneys(forceModel.getId()));
    }
    queryResult.close();
    return forceModel;
  }

  public void setSelectedJourney(int id, ArrayList<String> selectedJourneys){
    ModelList prevList = getSelectedJourneys(id);
    for (String itemId: selectedJourneys) {
      int selectedId = Integer.parseInt(itemId);
      if(prevList.containsKey(selectedId)){
        prevList.remove(selectedId);
      } else {
        DataModel model = new DataModel();
        model.initGeneralFields(selectedId, "", "");
        String sqlQuery = forceJourneyContract.add(model, id);
        dbHelpers.addNewItem(sqlQuery);
      }
    }
    for (DataModel item: prevList.values()) {
      String sqlQuery = forceJourneyContract.delete(item.getId());
      dbHelpers.deleteItem(sqlQuery);
    }
  }

  public ModelList getSelectedJourneys(int id) {
    StringBuilder involvedJourneys = new StringBuilder();
    involvedJourneys
      .append("SELECT ")
      .append(ForceJourneyContract.TABLE_NAME + "." + ForceJourneyContract.COLUMN_RELATION_ID + ", ")
      .append(JourneysContract.TABLE_NAME + "." + JourneysContract.COLUMN_TITLE)
      .append(" FROM ")
      .append(ForceJourneyContract.TABLE_NAME)
      .append(" INNER JOIN ")
      .append(JourneysContract.TABLE_NAME)
      .append(" ON ")
      .append(ForceJourneyContract.TABLE_NAME + "." + ForceJourneyContract.COLUMN_RELATION_ID)
      .append("=")
      .append(JourneysContract.TABLE_NAME + "." + JourneysContract._ID)
      .append(" WHERE ")
      .append(ForceJourneyContract.COLUMN_FORCE_ID)
      .append("=")
      .append(id)
    ;

    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(involvedJourneys.toString());

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int idColumnIndex = queryResult.getColumnIndex(ForceJourneyContract.COLUMN_RELATION_ID);
      int nameColumnIndex = queryResult.getColumnIndex(JourneysContract.COLUMN_TITLE);

      DataModel model = new DataModel();
      model.initGeneralFields(queryResult.getInt(idColumnIndex), queryResult.getString(nameColumnIndex), "");
      model.setSelected(true);
      result.addToList(model);
    }
    queryResult.close();
    return result;
  }
}
