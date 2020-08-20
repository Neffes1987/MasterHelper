package com.example.com.masterhelper.journey.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.journey.contracts.GoalContract;
import com.example.com.masterhelper.journey.models.GoalModel;
import com.example.com.masterhelper.force.models.RelationModal;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.com.masterhelper.settings.adapters.AbstractSetting;
import com.example.masterhelper.R;

public class GoalDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.goalContract;

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
  public void create(String name, String description, int parentId) {

  }

  @Override
  public void create(String name, String description, int parentId, String[] selectedItems) {
    GoalModel model = new GoalModel(0, name, description);

    model.setResult(getEnumBySelection(selectedItems));
    add(model, parentId);
  }

  private RelationModal.ResultType getEnumBySelection(String[] selectedItems){
    String inProgress = GlobalApplication.getAppContext().getResources().getString(R.string.force_goal_status_in_progress);
    String solved  = GlobalApplication.getAppContext().getResources().getString(R.string.force_goal_status_solved);
    String failed  = GlobalApplication.getAppContext().getResources().getString(R.string.force_goal_status_failed);
    if(inProgress.equals(selectedItems[0]))  {
      return RelationModal.ResultType.inProgress;
    }
    if(solved.equals(selectedItems[0]))  {
      return RelationModal.ResultType.solved;
    }
    if(failed.equals(selectedItems[0]))  {
      return RelationModal.ResultType.failed;
    }

    return null;
  }

  @Override
  public void update(int id, String name, String description, String[] selectedItems) {
    GoalModel model = new GoalModel(id, name, description);
    model.setResult(getEnumBySelection(selectedItems));
    update(model);
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

  @Override
  public ModelList list(int parentId) {
    String sqlQuery = GeneralContract.getListQuery(GoalContract.TABLE_NAME, null, GoalContract.COLUMN_JOURNEY_ID +"="+parentId, GoalContract._ID + " DESC", 0);
    ModelList result = new ModelList();
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int descriptionColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(GoalContract._ID);
      int statusColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_GOAL_STATUS);

      GoalModel model = new GoalModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex)
      );
      model.setResult(GoalModel.ResultType.valueOf(queryResult.getString(statusColumnIndex)));
      result.addToList(model);
    }
    queryResult.close();
    return result;
  }

  public DataModel get(int id) {
    String where = null;
    if(id != 0){
      where = GoalContract._ID+"="+ id;
    }
    String sqlQuery = GeneralContract.getListQuery(GoalContract.TABLE_NAME, null, where, GoalContract._ID + " DESC", 0);
    GoalModel model = null;
    Cursor queryResult = dbHelpers.getList(sqlQuery);

    while (queryResult.moveToNext()) {
      // Используем индекс для получения строки или числа
      int descriptionColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_DESCRIPTION);
      int titleColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_NAME);
      int idColumnIndex = queryResult.getColumnIndex(GoalContract._ID);
      int statusColumnIndex = queryResult.getColumnIndex(GoalContract.COLUMN_GOAL_STATUS);

      model = new GoalModel(
        queryResult.getInt(idColumnIndex),
        queryResult.getString(titleColumnIndex),
        queryResult.getString(descriptionColumnIndex)
      );
      model.setResult(GoalModel.ResultType.valueOf(queryResult.getString(statusColumnIndex)));
    }
    queryResult.close();
    return model;
  }
}
