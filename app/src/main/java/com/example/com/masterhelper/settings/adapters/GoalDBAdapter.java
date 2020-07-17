package com.example.com.masterhelper.settings.adapters;

import android.database.Cursor;
import com.example.com.masterhelper.core.app.GlobalApplication;
import com.example.com.masterhelper.core.contracts.GeneralContract;
import com.example.com.masterhelper.core.contracts.settings.GoalContract;
import com.example.com.masterhelper.core.force.models.GoalModel;
import com.example.com.masterhelper.core.force.models.RelationModal;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.models.utilities.ModelList;
import com.example.masterhelper.R;

public class GoalDBAdapter extends AbstractSetting {
  GeneralContract contract = dbHelpers.goalContract;

  @Override
  public void add(DataModel newModel) {
    String sqlQuery = contract.add(newModel, 0);
    dbHelpers.addNewItem(sqlQuery);
  }

  public void update(DataModel newModel) {
    String sqlQuery = contract.update(newModel.getId(), newModel);
    dbHelpers.updateItem(sqlQuery);
  }

  @Override
  public void create(String name, String description) {

  }

  @Override
  public void create(String name, String description, String[] selectedItems) {
    GoalModel model = new GoalModel(0, name, description);
    model.setResult(getEnumBySelection(selectedItems));
    add(model);
  }

  private RelationModal.ResultType getEnumBySelection(String[] selectedItems){
    int selectedItem = GlobalApplication.getAppContext().getResources().getIdentifier(selectedItems[0], "int", GlobalApplication.getAppContext().getPackageName());
    switch (selectedItem){
      case R.string.force_goal_status_in_progress: return RelationModal.ResultType.inProgress;
      case R.string.force_goal_status_solved: return RelationModal.ResultType.solved;
      case R.string.force_goal_status_failed: return RelationModal.ResultType.failed;
      default: return null;
    }
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
    String sqlQuery = GeneralContract.getListQuery(GoalContract.TABLE_NAME, null, null, GoalContract._ID + " DESC", 0);
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
}
