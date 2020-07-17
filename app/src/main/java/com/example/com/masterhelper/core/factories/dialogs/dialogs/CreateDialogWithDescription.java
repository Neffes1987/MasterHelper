package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.core.models.DataModel;
import com.example.com.masterhelper.core.factories.dialogs.ui.CreateNewItemDialog;

public class CreateDialogWithDescription extends CommonDialog {
  public CreateDialogWithDescription() {}

  @Override
  public void show(Activity context) {}

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.CONFIGURATION, CreateNewItemDialog.Configurations.withDescription.name());
    intent.putExtra(CreateNewItemDialog.TITLE, getTitle());
    if(settings != null){
      intent.putExtra(CreateNewItemDialog.ID, settings.getId());
      intent.putExtra(CreateNewItemDialog.OLD_NAME, settings.getName());
      intent.putExtra(CreateNewItemDialog.DESCRIPTION, settings.getDescription());
      context.startActivityForResult(intent, DIALOG_UPDATE_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_ACTIVITY_RESULT);
  }
}


