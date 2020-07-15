package com.example.com.masterhelper.core.factorys.dialogs.dialogs;

import android.app.Activity;
import android.content.Intent;
import com.example.com.masterhelper.core.appconfig.models.DataModel;
import com.example.com.masterhelper.core.factorys.dialogs.ui.CreateNewItemDialog;
import com.example.masterhelper.R;

public class CreateOneTextDialog extends CommonDialog {
  public CreateOneTextDialog() {}

  @Override
  public void show(Activity context) {}

  public void show(Activity context, DataModel settings) {
    Intent intent = new Intent(context, CreateNewItemDialog.class);
    intent.putExtra(CreateNewItemDialog.HIDE_DESCRIPTION, 1);
    if(settings != null){
      intent.putExtra(CreateNewItemDialog.TITLE, R.string.update);
      intent.putExtra(CreateNewItemDialog.IS_UPDATE, 1);
      intent.putExtra(CreateNewItemDialog.ID, settings.getId());
      intent.putExtra(CreateNewItemDialog.OLD_NAME, settings.getName());
      context.startActivityForResult(intent, DIALOG_UPDATE_ACTIVITY_RESULT);
      return;
    }
    context.startActivityForResult(intent, DIALOG_CREATE_ACTIVITY_RESULT);
  }
}


