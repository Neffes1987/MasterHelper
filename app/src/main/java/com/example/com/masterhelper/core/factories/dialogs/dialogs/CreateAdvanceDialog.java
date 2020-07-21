package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;
import com.example.com.masterhelper.force.models.AdvanceModel;
import com.example.com.masterhelper.force.models.RelationModal;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.masterhelper.R;


public class CreateAdvanceDialog extends CommonDialog {
  public CreateAdvanceDialog() {}

  @Override
  public void show(Activity context) {}

  private int getSelectedItemName(RelationModal.DirectionType direction){
    switch (direction){
      case advantage: return R.string.force_advantages_title;
      case disadvantage: return R.string.force_disadvantages_title;
    }
    return 0;
  }

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.CONFIGURATION, CreateNewItemDialog.Configurations.setting.name());
    intent.putExtra(CreateNewItemDialog.LIST_TITLES, new int[]{R.string.force_advantages_title, R.string.force_disadvantages_title});
    intent.putExtra(CreateNewItemDialog.TITLE, getTitle());
    if(settings != null){
      AdvanceModel advanceModel = (AdvanceModel) settings;
      intent.putExtra(CreateNewItemDialog.ID, settings.getId());
      intent.putExtra(CreateNewItemDialog.OLD_NAME, settings.getName());
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, settings.getDescription());
      intent.putExtra(CreateNewItemDialog.SELECTED_ITEMS, new int[]{getSelectedItemName(advanceModel.getDirection())});
      context.startActivityForResult(intent, DIALOG_UPDATE_SETTING_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_SETTING_ACTIVITY_RESULT);
  }
}


