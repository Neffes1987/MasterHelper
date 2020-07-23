package com.example.com.masterhelper.core.factories.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.force.ui.AddNewForce;
import com.example.com.masterhelper.core.models.DataModel;

public class CreateForceDialog extends CommonDialog {
  public CreateForceDialog() {}

  @Override
  public void show(Activity context) {}

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, AddNewForce.class);
    if(settings != null){
      intent.putExtra(AddNewForce.ID, settings.getId());
      context.startActivityForResult(intent, DIALOG_UPDATE_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_ACTIVITY_RESULT);
  }
}


