package com.example.com.masterhelper.core.components.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.core.components.dialogs.ui.CreateNewItemDialog;
import com.example.com.masterhelper.journey.models.GoalModel;
import com.example.com.masterhelper.force.models.RelationModal;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;

public class CreateGoalDialog extends CommonDialog {
  public CreateGoalDialog() {}

  public void show(Activity context) {}

  private int getSelectedItemName(RelationModal.ResultType direction){
    switch (direction){
      case solved: return R.string.force_goal_status_solved;
      case inProgress: return R.string.force_goal_status_in_progress;
      case failed: return R.string.force_goal_status_failed;
    }
    return 0;
  }

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.CONFIGURATION, CreateNewItemDialog.Configurations.setting.name());
    intent.putExtra(CreateNewItemDialog.LIST_TITLES, new int[]{
      R.string.force_goal_status_in_progress,
      R.string.force_goal_status_solved,
      R.string.force_goal_status_failed
    });
    intent.putExtra(CreateNewItemDialog.TITLE, getTitle());
    if(settings != null){
      GoalModel goalModel = (GoalModel) settings;
      intent.putExtra(CreateNewItemDialog.ID, settings.getId());
      intent.putExtra(CreateNewItemDialog.OLD_NAME, settings.getName());
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, settings.getDescription());
      intent.putExtra(CreateNewItemDialog.SELECTED_ITEMS, new int[]{ getSelectedItemName(goalModel.getResult()) });
      context.startActivityForResult(intent, DIALOG_UPDATE_SETTING_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_SETTING_ACTIVITY_RESULT);
  }
}


